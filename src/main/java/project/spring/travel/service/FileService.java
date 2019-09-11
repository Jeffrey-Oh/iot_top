package project.spring.travel.service;

import java.util.List;

import project.spring.travel.model.File;

public interface FileService {
	/**
	 * 파일 정보를 저장한다.
	 * @MethodName - insertFile
	 * @param file - 파일 데이터
	 * @throws Exception
	 * @author     - JeffreyOh
	 * @lastUpdate - 2019. 3. 27.
	 */
	public void insertFile(File file) throws Exception;
	
	/**
	 * 하나의 게시물에 종속된 파일 목록을 조회한다.
	 * @MethodName - selectFileList
	 * @param file - 게시물 일련번호를 저장하고 있는 JavaBeans
	 * @return 파일데이터 컬렉션
	 * @throws Exception
	 * @author     - JeffreyOh
	 * @lastUpdate - 2019. 3. 27.
	 */
	public List<File> selectFileList(File file) throws Exception;
	
	/**
	 * 단일 파일에 대한 정보를 조회한다.
	 * @MethodName - selectFile
	 * @param file
	 * @return BbsFile - 저장된 경로 정보
	 * @throws Exception
	 * @author     - JeffreyOh
	 * @lastUpdate - 2019. 4. 1.
	 */
	public File selectFile(File file) throws Exception;

	/**
	 * 단일 파일 정보를 삭제한다.
	 * @MethodName - deleteFile
	 * @param file
	 * @throws Exception
	 * @author     - JeffreyOh
	 * @lastUpdate - 2019. 4. 1.
	 */
	public void deleteFile(File file) throws Exception;
	
	/**
	 * 하나의 게시물에 종속된 파일 목록을 삭제한다.
	 * @MethodName - deleteFileAll
	 * @param file
	 * @throws Exception
	 * @author     - JeffreyOh
	 * @lastUpdate - 2019. 3. 29.
	 */
	public void deleteFileAll(File file) throws Exception;
}
