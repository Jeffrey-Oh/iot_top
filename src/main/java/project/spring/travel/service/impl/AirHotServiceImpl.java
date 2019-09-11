package project.spring.travel.service.impl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import project.spring.travel.model.AirHot;
import project.spring.travel.service.AirHotService;

@Service
public class AirHotServiceImpl  implements AirHotService{

	Logger logger = LoggerFactory.getLogger(AirHotServiceImpl.class);
	
	@Autowired
	SqlSession sqlSession;
	
	@Override
	public void sqlReset(AirHot airHot) throws Exception {
		try {
			sqlSession.insert("AirHotMapper.sqlReset", airHot);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getLocalizedMessage());
			throw new Exception("일련번호 초기화에 실패했습니다.");
		}
	}
	
	@Override
	public void addSql(AirHot airHot) throws Exception {
		try {
			sqlSession.insert("AirHotMapper.addSql", airHot);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getLocalizedMessage());
			throw new Exception("추천특가 데이터 추가에 실패했습니다.");
		}
	}
	
	@Override
	public AirHot getAirHotItem(AirHot airHot) throws Exception {
		AirHot item = null;
		
		try {
			item = sqlSession.selectOne("AirHotMapper.getAirHotItem", airHot);
			if (item == null) {
				throw new NullPointerException();
			}
		} catch (NullPointerException e) {
			e.printStackTrace();
			logger.error(e.getLocalizedMessage());
			throw new Exception("추천특가 데이터 상세 조회 값이 없습니다.");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getLocalizedMessage());
			throw new Exception("추천특가 데이터 상세 조회에 실패했습니다.");
		}
		
		return item;
	}

	@Override
	public List<AirHot> getAirHotList(AirHot airHot) throws Exception {
		List<AirHot> list = null;
		
		try {
			list = sqlSession.selectList("AirHotMapper.getAirHotList", airHot);
			if (list == null) {
				throw new NullPointerException();
			}
		} catch (NullPointerException e) {
			e.printStackTrace();
			logger.error(e.getLocalizedMessage());
			throw new Exception("추천특가 데이터 목록 조회 값이 없습니다.");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getLocalizedMessage());
			throw new Exception("추천특가 데이터 목록 조회에 실패했습니다.");
		}
		
		return list;
	}

	@Override
	public int getAirHotCnt() throws Exception {
		int cnt = 0;
		
		try {
			cnt = sqlSession.selectOne("AirHotMapper.getAirHotCnt");
			if (cnt == 0) {
				throw new NullPointerException();
			}
		} catch (NullPointerException e) {
			e.printStackTrace();
			logger.error(e.getLocalizedMessage());
			throw new Exception("추천특가 데이터가 없습니다.");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getLocalizedMessage());
			throw new Exception("추천특가 데이터 총 개수 조회에 실패했습니다.");
		}
		
		return cnt;
	}

}
