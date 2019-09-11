package project.spring.travel.service.impl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import project.spring.travel.model.AirSearch;
import project.spring.travel.service.SearchService;

/**
 * 항공권 DB 데이터 추가
 * 
 * @author - JEFFREY_OH
 * @lastupdate - 2019. 2. 13.
 * @filename - SearchServiceImpl.java
 */
@Service
public class SearchServiceImpl implements SearchService {

	private static Logger logger = LoggerFactory.getLogger(SearchServiceImpl.class);

	@Autowired
	SqlSession sqlSession;
	
	@Override
	public void sqlReset(AirSearch airSearch) throws Exception {
		try {
			sqlSession.insert("SearchMapper.sqlReset", airSearch);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getLocalizedMessage());
			throw new Exception("일련번호 초기화에 실패했습니다.");
		}
	}

	@Override
	public void addSql(AirSearch airSearch) throws Exception {
		try {
			sqlSession.insert("SearchMapper.addSql", airSearch);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getLocalizedMessage());
			throw new Exception("항공권 데이터 추가에 실패했습니다.");
		}
		
	}

	@Override
	public List<AirSearch> selectTicketOneWay(AirSearch airSearch) throws Exception {
		List<AirSearch> list = null;
		
		try {
			list = sqlSession.selectList("SearchMapper.selectTicketOneWay", airSearch);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getLocalizedMessage());
			throw new Exception("항공권 편도 목록 조회에 실패했습니다.");
		}
		
		return list;
	}

	@Override
	public int getCountList(AirSearch airSearch) throws Exception {
		int result = 0;
		
		try {
			result = sqlSession.selectOne("SearchMapper.getCountList", airSearch);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getLocalizedMessage());
			throw new Exception("항공권 목록 개수 조회에 실패했습니다.");
		}
		
		return result;
	}
}
