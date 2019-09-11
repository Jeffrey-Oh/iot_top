package project.spring.travel.service.impl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import project.spring.travel.model.CultureFestival;
import project.spring.travel.service.CultureFestivalService;

@Service
public class CultureFestivalServiceImpl implements CultureFestivalService {
	
	Logger logger = LoggerFactory.getLogger(CultureFestivalServiceImpl.class);
	
	@Autowired
	SqlSession sqlSession;

	@Override
	public void insertCultureFestival(CultureFestival cultureFestival) throws Exception {
		try {
            int cnt = sqlSession.update("CultureFestivalMapper.updateCultureFestival", cultureFestival);
            
            // 업데이트된 데이터의 수가 없을 경우 새로 등록 처리를 한다.
            if(cnt == 0) {
                int result = sqlSession.insert("CultureFestivalMapper.insertCultureFestival", cultureFestival);
                
                if(result == 0) {
                    // 수행된 행이 없다면 강제로 예외를 발생시킨다.
                    // -> 이 예외를 처리 가능한 catch블록으로 제어가 이동한다.
                    throw new NullPointerException();
                }
            }
        } catch (NullPointerException e) {
            throw new Exception("저장된 문화 축제 DB가 없습니다.");
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("문화 축제 DB 저장에 실패했습니다.");
        }
    } // End insertCultureFestival Method

	@Override
	public List<CultureFestival> selectCultureFestivalList(CultureFestival cultureFestival) throws Exception {
		
		List<CultureFestival> result = null;

        try {
            result = sqlSession.selectList("CultureFestivalMapper.selectCultureFestivalList", cultureFestival);
         
            
        } catch (Exception e) {
        	e.printStackTrace();
            throw new Exception("즐겨찾기 목록 조회에 실패했습니다."); // 사용자에게 표시될 메시지를 설정한다.
            
        }
		
		return result;		
		
	}

	@Override
	public int countCultureFestivalList(CultureFestival cultureFestival) throws Exception {
		int result = 0;
		
		try {
	            result = sqlSession.selectOne("CultureFestivalMapper.countCultureFestivalList", cultureFestival);
	            
	            // 업데이트된 데이터의 수가 없을 경우 Null 에러
	            if(result == 0) {
	                throw new NullPointerException();
	            }
	        } catch (NullPointerException e) {
	            throw new Exception("저장된 문화 축제 DB가 없어 카운트 실패!");
	        } catch (Exception e) {
	            e.printStackTrace();
	            throw new Exception("Count Failure");
	        }
		 
		 return result;
	}

	@Override
	public CultureFestival selectCultureFestivalItem(CultureFestival cultureFestival) throws Exception {
		CultureFestival result = null;
		
		try {
			result = sqlSession.selectOne("CultureFestivalMapper.selectCultureFestivalItem", cultureFestival);
			if (result == null) {
				throw new NullPointerException();
			}
		} catch (NullPointerException e) {			
			throw new Exception("문화 정보가 없습니다.");
		} catch (Exception e) {			
			throw new Exception("문화 정보 아이템 조회에 실패했습니다.");
		}

		return result;
	}
	
	

	@Override
	public void updateCultureFestByLikeUp(CultureFestival cultureFestival) throws Exception {
		try {
            int result = sqlSession.update("CultureFestivalMapper.updateCultureFestByLikeUp", cultureFestival);
         
            if(result == 0) {
                // 수행된 행이 없다면 강제로 예외를 발생시킨다.
                // -> 이 예외를 처리 가능한 catch블록으로 제어가 이동한다.
                throw new NullPointerException();
            }
        } catch (NullPointerException e) {
            throw new Exception("좋아요 증가를 할 공연정보가 없습니다.");
        } catch (Exception e) {
            logger.error(e.getLocalizedMessage());
            throw new Exception("문화 공연 좋아요 증가에 실패했습니다.");
        }
		
	}

	@Override
	public void updateCultureFestByLikeDown(CultureFestival cultureFestival) throws Exception {
		try {
            int result = sqlSession.update("CultureFestivalMapper.updateCultureFestByLikeDown", cultureFestival);
         
            if(result == 0) {
                // 수행된 행이 없다면 강제로 예외를 발생시킨다.
                // -> 이 예외를 처리 가능한 catch블록으로 제어가 이동한다.
                throw new NullPointerException();
            }
        } catch (NullPointerException e) {
            throw new Exception("좋아요 감소를 할 공연정보가 없습니다.");
        } catch (Exception e) {
            logger.error(e.getLocalizedMessage());
            throw new Exception("문화 공연 좋아요 감소에 실패했습니다.");
        }
		
	}

	@Override
	public CultureFestival selectCultureCount(CultureFestival cultureFestival) throws Exception {
		
		CultureFestival result;
		
		try {
	            result = sqlSession.selectOne("CultureFestivalMapper.selectCultureCount", cultureFestival);
	            
	            
	    } catch (Exception e) {
	            e.printStackTrace();
	            throw new Exception("Like Count Failure");
	    }
		 
		 return result;
	}

	@Override
	public CultureFestival selectCultureFestivalItemFL(CultureFestival cultureFestival) throws Exception {
		CultureFestival result = null;
		
		try {
			result = sqlSession.selectOne("CultureFestivalMapper.selectCultureFestivalItemFL", cultureFestival);
			if (result == null) {
				throw new NullPointerException();
			}
		} catch (NullPointerException e) {			
			throw new Exception("문화 정보가 없습니다.");
		} catch (Exception e) {			
			throw new Exception("문화 정보 아이템 조회에 실패했습니다.");
		}

		return result;
	}
	
	
	
		
	
	

	
	
}
