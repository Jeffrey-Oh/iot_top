package project.spring.travel.test.api;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import project.spring.travel.model.Code;
import project.spring.travel.service.CodeService;

/**
 * 공항 코드 API 연동 및 DB에 데이터 저장
 * @author     - JEFFREY_OH
 * @lastupdate - 2019. 2. 13.
 * @filename   - Code.java
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/**/*.xml"})
public class CodeAll {
	
	// 프로그램 로그 저장
	Logger logger = LoggerFactory.getLogger(CodeAll.class.getName());
	
	// DB session
	@Autowired
	SqlSession sqlSession;

	// Service 객체 생성
	@Autowired
	CodeService codeService;
	
	@Test
    public void testFactory() {

		// CodeBeans 형태의 List 결과를 담을 객체 생성
		List<Code> list = null;
		
		try {
			// API 연동 및 DB 데이터 저장 후 데이터 조회
			codeService.getAPI();
			list = codeService.getItemList();

//			FileHelper f = FileHelper.getInstance();
//			String filePath = "C:/Users/OhEun/Downloads/1.csv";
//			String content = "";
//			String encType = "EUC-KR";
			// 조회한 데이터 추출
			for (int i = 0; i < list.size(); i++) {
				Code obj = list.get(i);
//				content +=  obj.getCityCode() + ", " + obj.getCityKor() + "\n";
				logger.debug("조회된 데이터 >> " + obj.toString());
				
			}
//			f.writeString(filePath, content, encType);
		} catch (Exception e) {
			logger.error("데이터 저장에 실패했습니다." + e.getMessage());
		}
	}
}
