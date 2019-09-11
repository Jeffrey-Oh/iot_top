package project.spring.travel.controller;

import java.io.IOException;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import project.spring.helper.UploadHelper;
import project.spring.helper.WebHelper;

/**
 * @fileName    : Download.java
 * @author      : 홍준성
 * @description : 첨부파일 다운로드를 처리하기 위한 컨트롤러
 *     - 회원가입시 업로드 한 프로필 이미지에 대한 썸네일을
 *         웹 페이지에 노출시키기 위해서는 파일 다운로드 기능이 필요하다.
 *     - 파일 다운로드를 수행하기 위한 서블릿을 구성하고,
 *         이 서블릿에 대한 URL을 <img> 태그의 src 속성에 지정하여
 *         업로드 된 이미지를 표시할 수 있다.
 * @lastDate    : 2019. 4. 18.
 */
/** 컨트롤러 선언 */
@Controller
public class Download {
    /**
     * ===== Helper 객체의 선언과 할당 처리 =====
     */
    /** (1) 사용하고자 하는 Helper + Service 객체 선언 */
    // -> import org.slf4j.Logger;
    // -> import org.slf4j.LoggerFactory;
    Logger logger = LoggerFactory.getLogger(Download.class); // Log4j 객체 직접 생성
    // -> import project.jsp.helper.WebHelper
    @Autowired
    WebHelper web;
    // -> import project.jsp.helper.UploadHelper
    @Autowired
    UploadHelper upload;

    @RequestMapping(value = "/download.do", method = RequestMethod.GET)
    public String doRun(Locale locale, Model model, 
            HttpServletRequest request, HttpServletResponse response) {
        /**
         * ===== redirect 메서드 처리와 다운로드 스트림 처리 수정 =====
         * - Spring 방식에 맞게 수정한 Download 기능은
         *     response 내장객체를 메서드 내에서 스스로 생성하기 때문에
         *     전달 파라미터에서 response객체가 제거된다.
         */
        /** (2) WebHelper 초기화 */
        web.init(); // Helper, Service 객체들의 할당은 자동화 되므로 삭제하고 WebHelper를 초기화 한다.
        
        /**
         * ===== 파일 다운로드 처리 =====
         * - 파일 다운로드에 대한 자세한 내용은 "FileUpload"를 참고하자.
         */
        /** (3) 파라미터 받기 */
        // 서버상에 저장되어 있는 파일경로 (필수)
        String filePath = web.getString("file");
        // 원본 파일이름 (미필수)
        String orginName = web.getString("orgin");
        
        /** (4) 다운로드 스트림 출력하기 */
        /**
         * ===== 다운로드 기능을 수행하는 JSP가
         * 축소될 크기와 크롭 기능 사용 여부를 의미하는 파라미터를 받을 수 있도록 추가한다.
         * =====
         */
        // 축소될 이미지 해상도 -> 320x320
        String size = web.getString("size");
        // 이미지 크롭 여부 -> 값이 없을 경우 기본값 false
        String crop = web.getString("crop", "false");
        
        /** 다운로드 스트림 출력하기 */
        if(filePath != null) {
            try {
                /**
                 * ===== 다운로드 처리를 파라미터에 따라 분기 =====
                 * - size값이 있을 경우 썸네일에 대한 다운로드를 수행하도록 if문을 구성한다.
                 */
                if(size != null) {
                    // x를 기준으로 나눠서 숫자로 변환
                    String[] temp = size.split("x");
                    int width = Integer.parseInt(temp[0]);
                    int height = Integer.parseInt(temp[1]);
                    
                    // 크롭 여부를 boolean으로 설정
                    boolean is_crop = false;
                    if(crop.equals("true")) {
                        is_crop = true;
                    }
                    // 썸네일 생성 후 다운로드 처리
                    logger.debug("Create Thumbnail Image -> " + filePath);
                    upload.printFileStream(filePath, width, height, is_crop);
                } else {
                    logger.debug("Create Thumbnail Image -> " + filePath);
                    upload.printFileStream(filePath, orginName);
                }
            } catch(IOException e) {
                logger.debug(e.getLocalizedMessage());
            }
        }
        
        return null;
    }
}
