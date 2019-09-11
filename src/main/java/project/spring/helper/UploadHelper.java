package project.spring.helper;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.activation.MimetypesFileTypeMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.Thumbnails.Builder;
import net.coobird.thumbnailator.geometry.Positions;

/**
 * @fileName : UploadHelper.java
 * @author : 홍준성
 * @description : 업로드 기능을 구현하기 위한 싱글톤 클래스
 * @lastUpdate : 2019-04-19
 */
public class UploadHelper {
    /** ===== 업로드에 필요한 상수 및 객체 선언 ===== */
//    /** 자신의 Workspace 경로 */
//    public static final String homeDir = "D:/HongIot";
    /**
     * ===== root-context.xml에 의해서 전달받는 경로를 활용하여
     *     업로드 관련 폴더의 변수값을 재구성하기 =====
     * - 필요한 멤버변수를 정의한다. (기존에는 상수형태로 사용되었음)
     * - 멤버변수를 초기화 하는데 필요한 값을 생성자를 통해서 주입받는다.
     */
    /** 업로드 된 결과물이 저장될 폴더 */
    public String fileDir = null;
    /** 업로드가 진행될 임시 폴더 */
    public String tempDir = null;
    
    public UploadHelper(String homeDir) {
        super();
        this.fileDir = homeDir; // 현 프로젝트에서는 root-context.xml에 Z:/ (= upload 폴더) 로 경로를 잡았음으로 이 안에 생성하기에 + "/upload"
        this.tempDir = fileDir + "/temp";
    }

    /**
     * Multipart로 전송되는 데이터는 하나의 컬렉션 객체 안에
     * String과 File 데이터가 뒤섞인 상태이기 때문에
     * JSP에서는 업로드 정보에서 텍스트 정보와 파일 정보를 구분하여 분류해야 한다.
     */
    /** File정보를 저장하기 위한 컬렉션 */
    private List<FileInfo> fileList;
    
    /** 그 밖의 일반 데이터를 저장하기 위한 컬렉션 */
    private Map<String, String> paramMap;
    
    /** 업로드된 파일의 리스트를 리턴한다. */
    public List<FileInfo> getFileList() {
        return this.fileList;
    }
    
    /** 업로드시에 함께 전달된 파라미터들의 컬렉션을 리턴한다. */
    public Map<String, String> getParamMap() {
        return this.paramMap;
    }
    
    /**
     * ===== 업로드를 수행하기 위한 메서드 정의와 구현절차 확인 =====
     * - multipart로 전송된 데이터들은 request 객체가 처리할 수 없기 때문에,
     *     commons-file-upload 라이브러리를 통해
     *     request 객체 안에 포함된 전송 정보를 추출해 내야 한다.
     * - 이 메서드 안에서 발생되는 예외를, 이 메서드를 호출하는 측에 의탁한다.
     */
    /**
     * ===== JSP 내장객체를 요구하는 메서드의 개선 =====
     * - Spring에서는 JSP의 내장객체를 Helper가 스스로 생성할 수 있기 때문에
     *     컨트롤러와의 의존성을 약하게 하여 프로그램의 유지보수에 편리함을 준다.
     */
    /**
     * Multipart로 전송된 데이터를 판별하여 파일리스트와 텍스트 파라미터를 분류한다.
     * @throws Exception
     */
    public void multipartRequest() throws Exception {
        /** JSP 내장객체를 담고 있는 Spring의 객체를 통해서 내장객체 획득하기 */
        // -> import org.springframework.web.context.request.RequestContextHolder;
        // -> import org.springframework.web.context.request.ServletRequestAttributes;
        ServletRequestAttributes requestAttr = 
                (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttr.getRequest();
        
        /**
         * ===== 업로드를 위한 준비 과정 (1) =====
         * - 전송 데이터가 multipart인지 검사
         * - 업로드 파일이 저장될 폴더와 업로드 처리가 진행될 임시 폴더 생성하기
         */
        /** multipart로 전송되었는지 여부 검사 */
        // -> import org.apache.commons.fileupload.servlet.ServletFileUpload;
        boolean isMultipart = ServletFileUpload.isMultipartContent(request);
        
        if(!isMultipart) {
            // 전송될 데이터가 없으므로 강제 예외 발생
            throw new Exception();
            /**
             * ===== 참고) throw 구문의 제어 흐름 =====
             * ## 강제 예외 발생
             * - throw 구문을 만나면 JVM은 그 위치를 예외 상황으로 인식한다.
             * - throw 구문을 사용할 경우, 그 위치를 try~catch로 묶거나
             *     메서드 선언시에 throws를 지정해야 한다.
             *     
             * - 호출된 메서드 내에서 throw구문을 만나면,
             *     호출한 위치에서는 예외로 감지되어 catch 블록이 실행된다.
             */
        }
        
        /** 폴더의 존재 여부 체크해서 생성하기 */
        // -> import java.io.File
        File uploadDirFile = new File(fileDir);
        if(!uploadDirFile.exists()) {
            uploadDirFile.mkdirs();
        }
        
        File tempDirFile = new File(tempDir);
        if(!tempDirFile.exists()) {
            tempDirFile.mkdirs();
        }
        
        /** 업로드가 수행될 임시 폴더 연결 */
        // -> import org.apache.commons.fileupload.disk.DiskFileItemFactory;
        DiskFileItemFactory factory = new DiskFileItemFactory();
        factory.setRepository(tempDirFile);
        
        /** 
         * ### 이 구문이 실행되면 사실상 모든 데이터가 items객체에 저장된다.
         * (텍스트 데이터와 파일 데이터가 일괄 저장됨.)
         */
        /** 업로드 시작 */
        ServletFileUpload upload = new ServletFileUpload(factory);
        // UTF-8 처리 지정
        upload.setHeaderEncoding("UTF-8");
        // 최대 파일 크기 -> 20M
        upload.setSizeMax(20 * 1024 * 1024);
        // 실제 업로드를 수행하여 파일 및 파라미터들을 얻기
        List<FileItem> items = upload.parseRequest(request);
        
        /**
         * ### Items 객체 안에 저장된 데이터를
         * 미리 준비해둔 컬렉션에 텍스트와 파일로 분류해서
         * 추출해야 한다. 
         */
        // itemas에 저장 데이터가 분류될 컬렉션들 할당하기
        fileList = new ArrayList<FileInfo>();
        paramMap = new HashMap<String, String>();
        
        /** 업로드 된 파일의 정보 처리 */
        for(int i = 0; i < items.size(); i++) {
            /**
             * ===== 전송된 데이터의 수 만큼 반복처리 하기 =====
             * - 반복문 안에서
             *     각각의 데이터 요소들을 텍스트와 파일로 구분하기 위한
             *     if문을 구성한다.
             */
            // 전송된 정보 하나를 추출한다.
            // -> import 
            FileItem f = items.get(i);
            
            if(f.isFormField()) {
                /**
                 * ===== 텍스트 데이터인 경우 key와 value 형태의 Map에 분류
                 * - 체크박스의 경우는 같은 이름으로 여러 개의 데이터가 전송되기 때문에
                 *     이 데이터들을 콤마로 구분하여 하나의 key에 묶어둔다.
                 */
                /** 파일 형식의 데이터가 아닌 경우 -> paramMap에 정보 분류 */
                String key = f.getFieldName();
                // value를 UTF-8 형식으로 취득한다.
                String value = f.getString("UTF-8");
                
                // 이미 동일한 키 값이 map 안에 존재한다면? -> checkbox
                if(paramMap.containsKey(key)) {
                    // 기존의 값 뒤에 콤마(,)를 추가해서 값을 병합한다.
                    String new_value = paramMap.get(key) + "," + value;
                    paramMap.put(key, new_value);
                } else {
                    // 그렇지 않다면 키와 값을 신규로 추가한다.
                    paramMap.put(key, value);
                }
            } else {
                /**
                 * ===== 파일 형식의 데이터인 경우 업로드 처리 절차 =====
                 * - HTML에서 전송된 데이터들은 임시 폴더에 우선적으로 저장된다.
                 * - 이 파일들의 정보를 추출해서 원하는 경로에 복사하고,
                 *     임시파일을 삭제해야 한다.
                 * - 이 과정에서 파일의 이름, 용량, 형식 등의 부가 정보를 획득할 수 있다.
                 */
                /** 파일 형식의 데이터인 경우 -> fileList에 정보 분류 */
                
                /** ===== 파일의 부가 정보 획득하기 ===== */
                /** 1) 파일의 정보를 추출한다. */
                String orginName = f.getName();         // 파일의 원본 이름
                String contentType = f.getContentType(); // 파일 형식
                long fileSize = f.getSize();             // 파일 사이즈
                
                // 파일 사이즈가 없다면 조건으로 돌아간다.
                if(fileSize < 1) {
                    continue;
                }
                
                // 파일이름에서 확장자만 추출
                String ext = orginName.substring(orginName.lastIndexOf("."));
                
                /** ===== 임시 폴더의 파일을 원하는 경로로 복사하기 (파일명 중복 방지) */
                /** 2) 동일한 이름의 파일이 존재하는지 검사한다. */
                // 웹 서버에 저장될 이름을 "현재의 Timestamp+확장자(ext)"로 지정 (중복저장 우려)
                String fileName = System.currentTimeMillis() + ext;
                // 저장된 파일 정보를 담기 위한 File객체
                File uploadFile = null;
                // 중복된 이름의 파일이 존재할 경우 index값을 1씩 증가하면서 뒤에 덧붙인다.
                int index = 0;
                
                // 일단 무한루프
                while(true) {
                    // 업로드 파일이 저장될 폴더 + 파일이름으로 파일객체를 생성한다.
                    uploadFile = new File(uploadDirFile, fileName);
                    
                    // 동일한 이름의 파일이 없다면 반복 중단
                    if(!uploadFile.exists()) {
                        break;
                    }
                    
                    // 그렇지 않다면 파일이름에 index값을 적용하여 이름 변경
                    fileName = System.currentTimeMillis() + (++index) + ext;
                } // End while
                
                // 최종적으로 구성된 파일객체를 사용해서
                // 임시 폴더에 존재하는 파일을 보관용 폴더에 복사하고, 임시파일 삭제
                f.write(uploadFile);
                f.delete();
                
                /**
                 * ===== 업로드 된 파일의 정보를 Beans 객체로 묶어서 List에 저장 =====
                 * - 멀티 업로드가 수행될 경우 여러 개의 파일이 전송 될 수 있기 때문에,
                 *     업로드 된 파일의 정보는 List에 저장한다.
                 */
                /** 3) 파일 저장 및 파일 정보 분류 처리 */
                // 생성된 정보를 Beans의 객체로 설정해서 컬렉션에 저장한다.
                // -> 이 정보는 추후 파일의 업로드 내역을 DB에 저장할 때 사용된다.
                FileInfo info = new FileInfo();
                info.setOrginName(orginName);
                info.setFileDir(fileDir);
                info.setFileName(fileName);
                info.setContentType(contentType);
                info.setFileSize(fileSize);
                
                fileList.add(info);
            } // End if~else
        } // End for
        
    } // End multipartRequest Method
    
    /** ===== 다운로드 기능을 수행하기 위한 메서드 정의하기 ===== */
    /**
     * ===== JSP 내장객체를 요구하는 메서드의 개선 =====
     * - 앞 페이지에서 생성한 request객체 뿐만 아니라 response 객체도
     *     스스로 생성 가능하다. (스프링 4.x)부터 가능
     */
    /**
     * 지정된 경로의 파일을 읽어들인다. 그 내용을 응답객체(response)를 사용해서 출력한다.
     * @param filePath - 서버상의 파일 경로
     * @param orginName - 원본 파일 이름
     * @throws IOException
     */
    public void printFileStream(String filePath, String orginName) throws IOException {
        /** JSP 내장객체를 담고 있는 Spring의 객체를 통해서 내장객체 획득하기 */
        // -> import org.springframework.web.context.request.RequestContextHolder;
        // -> import org.springframework.web.context.request.ServletResponseAttributes;
        ServletRequestAttributes requestAttr = 
                (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletResponse response = requestAttr.getResponse();
        
        /**
         * ===== 파일의 존재 여부 확인하기 + 파일의 기본 정보 추출하기 =====
         * - 파라미터로 전달되는 원본 파일명은,
         *     다운로드 시 서버에 저장되어 있는 timestamp 기반의 파일명을
         *     원본 이름으로 변환시키기 위해 사용된다.
         */
        /** 파일의 존재여부를 확인하고 파일의 정보 추출하기 */
        // -> import java.io.File;
        File f = new File(filePath);
        if(!f.exists()) {
            // -> import java.io.FileNotFoundException;
            throw new FileNotFoundException("파일이 존재하지 않습니다. >>> " + f.getAbsolutePath());
        }
        
        // 파일의 크기 추출하기
        long size = f.length();
        // 서버에 보관되어 있는 파일의 이름 추출하기
        String name = f.getName();
        
        // 원본 파일명이 전달되지 않은 경우 서버상의 파일이름으로 대체
        if(orginName == null) {
            orginName = name;
        }
        
        // 파일형식 얻기 (업로드 정보에서 추출했던 contentType과 같은 값)
        // -> import javax.activation.MimetypesFileTypeMap;
        MimetypesFileTypeMap typeMap = new MimetypesFileTypeMap();
        String fileType = typeMap.getContentType(f);
        
        /**
         * ===== 웹 브라우저가 JSP 페이지를 파일로 인식하게 하기 위한 처리 =====
         * - reset: 파일 데이터는 공백이 하나라도 추가될 경우
         *     깨진 상태로 다운로드 된다. 비정상 데이터가 함께 출력되는 것을
         *     방지하기 위해 응답 기능을 담당하는 response 객체의 내부를 모두 비운다.
         * - setHeader: response 객체를 통해 Content-Type값을
         *     "text/html"이 아닌 다른 형식으로 지정할 경우
         *     웹 브라우저는 웹 페이지를 다른 형태의 파일로 인식한다.
         *     - image/jpg, image/png, image/gif (이미지),
         *         application/vnd.ms-excel (엑셀)
         */
        /** 브라우저에게 이 메서드를 호출하는 페이지의 형식을 일반  파일로 인식시키기 위한 처리 */
        // 다른 데이터와 섞이지 않도록 응답객체(response)를 리셋한다.
        response.reset();
        
        // 파일형식 정보 설정
        response.setHeader("Content-Type", fileType + "; charset=UTF-8");
        
        // 파일의 이름 설정 (인코딩 필요함)
        // -> import java.net.URLEncoder;
        String encFileName = URLEncoder.encode(orginName, "UTF-8");
        response.setHeader("Content-Disposition", "attachment; filename=" + encFileName + ";");
        
        // 파일의 용량 설정
        response.setContentLength((int) size);
        
        /**
         * ===== 파일 읽어들이기 =====
         * - InputStream 객체를 사용해서 파일의 바이너리를 byte 배열에 담는다.
         *     -> 스트림 생성
         */
        /** 스트림을 통한 파일의 바이너리 읽기 */
        // 파일 읽기 스트림을 생성한다.
        // -> import java.io.InputStream;
        // -> import java.io.FileInputStream;
        InputStream is = new FileInputStream(f);
        
        // is는 한번에 내용을 읽어야 하지만 BufferedInputStream은 조금씩 나누어 읽어들일 수 있다.
        // 대용량 파일 처리를 위해서는 이 클래스를 통해 데이터를 조금씩 나누어 처리해야 한다.
        // -> import java.io.BufferedInputStream;
        BufferedInputStream bis = new BufferedInputStream(is);
        
        // BufferedInputStream을 통해 읽어들인 데이터를 출력하기 위해 사용되는 클래스
        // -> import java.io.BufferedOutputStream;
        BufferedOutputStream bos = new BufferedOutputStream(response.getOutputStream());
        
        // 업로드 된 파일의 용량에 상관없이 1kbyte 크기의 배열 공간을 생성한다.
        byte[] buffer = new byte[1024];
        
        // 버퍼링이 수행되는 동안 읽어들인 데이터의 크기를 체크하기 위한 변수
        int length = 0;
        
        /**
         * ===== 동작 원리 =====
         * bis.read() 메서드는 파라미터로 전달된 배열 길이만큼 데이터를 담고
         * 자신이 읽어들인 용량을 리턴한다.
         * 만약 읽어들인 내용이 없다면 -1을 리턴한다.
         * ex) 2000byte를 읽어야 할 경우
         *     첫 번째 수행 시 버퍼링이 => 1024 => buffer배열은 0부터 1024칸이 채워져 있음.
         *     두 번째 수행 시 버퍼링이 => 976 => buffer 배열은 0부터 976칸이 채워져 있음.
         *     세 번째 수행 시 버펑링이 => -1 (종료)
         * 그러므로 이 값이 -1이 아닐때까지 반복한다면
         * 파일을 1024바이트씩 나누어서 여러번 읽어들인다.
         * -> 버퍼링 처리
         */
        while((length = bis.read(buffer)) != -1) {
            // buffer의 내용을 0번째 위치부터 읽어들인 크기만큼까지만
            // 버퍼링을 위한 스트림(bos)에 옮겨 담는다.
            bos.write(buffer, 0, length);
        }
        // 옮겨담은 내용을 출력한다.
        bos.flush();
        // 모든 스트림들을 담는다.
        bos.close();
        bis.close();
        is.close();
        
    } // End printFileStream Method
    
    /** ===== 썸네일 이미지 생성 기능을 구현하기 위한 메서드 정의 ===== */
    /**
     * 리사이즈 된 썸네일 이미지를 생성한다.
     * @param loadFile - 원본 파일의 경로
     * @param width - 최대 이미지 가로 크기
     * @param height - 최대 이미지 세로 크기
     * @param crop - 이미지 크롭 사용 여부
     * @return 생성된 이미지의 절대 경로
     * @throws IOException
     */
    public String createThumbnail(String loadFile, int width, int height, boolean crop)
        throws IOException {
        /** ===== 썸네일 이미지가 저장될 파일 경로 준비 ===== */
        /** 생성된 썸네일 이미지의 경로 */
        String saveFile = null;
        
        /** 원본 파일명에서 저장될 파일 경로를 생성한다. */
        File load = new File(loadFile);
        String dirPath = load.getParent();
        String fileName = load.getName();
        
        /** 원본 파일이름에서 이름과 확장자를 분리한다. */
        int p = fileName.lastIndexOf(".");
        String name = fileName.substring(0, p);
        String ext = fileName.substring(p + 1);
        
        /** 원본이름에 요청된 사이즈를 덧붙여서 생성될 파일명을 구성한다. */
        // ex) myphoto.jpg -> myphoto_resize_320x240.jpg
        String prefix = "_resize_";
        if(crop) {
            prefix = "_crop_";
        }
        
        String thumbName = name + prefix + width + "x" + height + "." + ext;
        File f = new File(dirPath, thumbName);
        
        /** 절대경로 추출 */
        saveFile = f.getAbsolutePath();
        
        /** 
         * ===== 썸네일 이미지 생성하기 =====
         * - thumbnailator 라이브러리의 상세 기능은
         *     https://coobird.github.io/thumbnailator/javadoc/0.4.8/ 를 참고한다.
         */
        /** 해당 경로에 이미지가 없는 경우만 수행 */
        if(!f.exists()) {
            // 원본 이미지 가져오기
            // -> import.net.coobird.thumbnailator.Thumbnails;
            // -> import.net.coobird.thumbnailator.Thumbnails.Builder;
            Builder<File> builder = Thumbnails.of(loadFile);
            // 이미지 크롭 여부
            if(crop == true) {
                builder.crop(Positions.CENTER);
            }
            // 축소할 사이즈 지정
            builder.size(width, height);
            // 세로로 촬영된 사진을 회전시킴
            builder.useExifOrientation(true);
            // 파일의 확장명 지정
            builder.outputFormat(ext);
            // 저장할 파일이름 지정
            builder.toFile(saveFile);
        }
        
        return saveFile;
    } // End createThumbnail Method
    
    /** ===== 썸네일 이미지를 생성한 후, 썸네일의 스트림을 웹 페이지에 출력하도록 메서드 재정의 ===== */
    /**
     * ===== JSP 내장객체를 요구하는 메서드의 개선 =====
     * - 이 메서드가 호출하는 메서드(createThumbnail)에서
     *     response 객체를 스스로 생성하고 있기 때문에,
     *     파라미터를 통한 전달이 필요 없어진다.
     */
    /**
     * 원본파일의 경로와 함께 이미지의 가로, 세로 크기가 전달될 경우,
     * 지정된 크기로 썸네일 이미지를 생성하고, 생성된 썸네일을 출력시킨다.
     * @param filePath - 원본 이미지 경로
     * @param width - 가로 크기
     * @param height - 세로 크기
     * @param crop - 크롭 여부
     * @throws IOException
     */
    public void printFileStream(String filePath, int width, int height, boolean crop)
        throws IOException {
        // 썸네일을 생성하고 경로를 리턴받는다.
        String thumbPath = this.createThumbnail(filePath, width, height, crop);
        
        /**
         * 썸네일을 출력한다.
         * -> 이 메서드를 호출하기 위해서 try~catch가 요구되지만,
         *     현재 메서드 역시 throws를 명시했기 때문에
         *     예외처리가 현재 메서드를 호출하는 곳으로 이관된다.
         */
        this.printFileStream(thumbPath, null);
    } // End printFileStream Method
    
    /**
     * ===== 파일 삭제를 위한 메서드 정의 =====
     * - 가입시에 업로드한 프로필 이미지도 탈퇴가 수행되는 과정에서
     *     함께 삭제되어야 한다.
     * - 업로드 된 파일의 이름이 helloworld.png 이고, 이 파일에서 생성된
     *     썸네일 이미지의 이름이 helloworld_crop_40x40.png인 경우
     *     공통적으로 helloworld라는 단어를 포함한 모든 파일이 일괄 삭제되어야 한다.
     */
    /**
     * 전달된 경로에 대한 파일이 실제로 존재할 경우
     * 해당 파일과 비슷한 이름을 갖는 썸네일을 일괄 삭제한다.
     * @param filePath
     */
    public void removeFile(String filePath) {
        /**
         * ===== 파일의 존재 여부 검사 =====
         * - 경로값이 전달되지 않거나 존재하는 파일이 아닌 경우
         *     처리를 중단하기 위해 return 한다.
         */
        /** (1) 파일의 존재 여부 검사 */
        // 경로값이 존재하지 않는다면 처리 중단
        if(filePath == null) {
            return;
        }
        
        // 주어진 경로에 대한 파일 객체 생성
        File file = new File(filePath);
        
        // 실제로 존재하는지 검사한다.
        if(!file.exists()) {
            return;
        }
        
        /**
         * ===== 주어진 경로에서 파일의 이름과 디렉토리 경로 추출하기 =====
         */
        /** (2) 경로에서 파일이름 (확장자 제외), 폴더경로 추출 */
        // 첨부파일의 이름에서 확장자를 제외하고 추출
        String fileName = file.getName();
        final String prefix = fileName.substring(0, fileName.lastIndexOf("."));
        
        // 파일이 저장되어 있는 폴더에 대한 객체 생성
        // -> 이 안의 파일 목록을 스캔해야 한다.
        File dir = file.getParentFile();
        
        /**
         * ===== 디렉토리 안에서 파일이름이 비슷한 파일의 목록을 스캔한다. =====
         * - String[] list : java.io.File 클래스의 list 메서드는 FilenameFilter의
         *     객체를 파라미터로 요구한다.
         *     - FilenameFilter는 accept() 메서드를 재정의해서
         *         추출할 파일이름의 규칙을 정의한다.
         *         - 파일 이름의 규칙 검사를 수행한 후 true가 리턴되면
         *             String[] list 배열에 파일이름이 저장된다.
         */
        /** (3) 정해진 폴더 안에서 파일이름을 갖는 모든 파일의 목록을 추출 */
        // 폴더객에게 필터랑 규칙을 적용하여 일치하는 규칙의 파일의 이름들을 배열로 받는다.
        String[] list = dir.list(new FilenameFilter() {
            /**
             * dir객체가 의미하는 폴더 내의 모든 파일의 이름을
             * 이 메서드에게 전달하게 된다.
             * 이 메서드에서는 전달받은 이름이 원하는 규칙과 호환되는지를
             * 판별하여 true/false를 리턴한다.
             */
            @Override
            public boolean accept(File dir, String name) {
                /**
                 * 파일이름에 원본파일이름이 포함되어 있다면 true
                 * ex) 원본이름이 helloword일 경우
                 *     helloworld_crop_40x40, helloworld_resize_120x80 등의 파일이름이 추출된다.
                 */
                return (name.indexOf(prefix) > -1);
            }
        });
        
        /**
         * ===== 삭제할 파일 이름이 저장된 배열에 대한 처리 =====
         * - 배열의 항목 수 만큼 반복하면서 배열의 항목이 지정하는 파일을 삭제한다.
         */
        /** (4) 조회된 파일 목록을 순차적으로 삭제한다. */
        for(int j = 0; j < list.length; j++) {
            File f = new File(dir, list[j]);
            if(f.exists()) {
                f.delete();
            }
        }
        
    } // End removeFile Method
    
    /**
     * form의 multipart/form-data에서 받게된 <input tye="file" />은
     * UploadHelper의 multipartRequest Method를 통해 지정된 폴더에
     * 파일이 저장되게 되는데 예외발생이 일어났음에도 파일이 삭제되지 않기에
     * 갈 길을 잃은 파일을 삭제시키기 위한 Method 이다.
     * 사용방법: multipartRequest 메서드가 실행된 이후의 모든 예외처리마다 호출해준다.
     * @param upload - multipartRequest로 인해 담겨진 값
     */
    public void removeTempFile() {
        if(this.getFileList().size() != 0) {
            /** 예외발생시 임시폴더에 저장에 저장된 파일을 삭제해야 한다. */
            List<FileInfo> tempFileList = this.getFileList();
            // 임시저장 된 파일의 수 만큼 반복 처리 한다.
            for(int i = 0; i < tempFileList.size(); i++) {
                // 임시저장 된 정보 하나 추출
                // -> 임시저장 된 정보를 삭제하기
                FileInfo info = tempFileList.get(i);
                this.removeFile(info.getFileDir() + "/" + info.getFileName());
            }
        }
    } // End removeTempFile Method
    
}
