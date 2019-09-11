package project.spring.travel.service.impl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import project.spring.travel.model.Favorite;
import project.spring.travel.service.FavoriteService;

@Service
public class FavoriteServiceImpl implements FavoriteService {
	/**
     * 생성자 추가
     * MyBatis를 통하여 Mapper에 정의된 SQL구문의 실행을 요청
     */

	@Autowired
	SqlSession sqlSession;


	@Override
	public void addFavorite(Favorite favor) throws Exception {
		try {
            // insert, update, delete 중 하나의 메서드를 호출한다.
            // 파라미터1: MyBatis Mapper에 정의한 기능 이름
            // 파라미터2: 입력, 수정, 삭제할 데이터를 담고 있는 Beans 객체
            int result = sqlSession.insert("FavoriteMapper.addFavorite", favor);
            if(result == 0) {
                throw new NullPointerException();
            }
        } catch(NullPointerException e) {

            throw new Exception("즐겨찾기 버튼에 해당하는 게시물이 없습니다."); // 처리 결과가 없는 경우 사용자에게 표시할 메시지
        } catch(Exception e) {

            throw new Exception("즐겨찾기 저장에 실패했습니다."); // 처리 도중 에러가 발생할 경우 사용자에게 표시할 메시지
        }
	}

	@Override
	public void deleteFavorite(Favorite favor) throws Exception {
		
        try {
            int result = sqlSession.delete("FavoriteMapper.deleteFavorite", favor);
         
            if(result == 0) {
                // 수행된 행이 없다면 강제로 예외를 발생시킨다.
                // -> 이 예외를 처리 가능한 catch블록으로 제어가 이동한다.
                throw new NullPointerException();
            }
        } catch (NullPointerException e) {

            throw new Exception("입력된 즐겨찾기가 없습니다.");
        } catch (Exception e) {

                throw new Exception("즐겨찾기에 실패했습니다.");
        }
		
	}



	@Override
	public int favoriteExist(Favorite favor) throws Exception {
		int result = 0;
        
        try {
            // 즐겨찾기가 있는지 카운트한다 있으면 1, 없으면 0
        	result = sqlSession.selectOne("FavoriteMapper.selectFavoriteExist", favor);            
            
        } catch (Exception e) {            
            throw new Exception("데이터 조회에 실패했습니다.");
        } // End try~catch
        
        
		return result;
	}



	@Override
	public List<Favorite> selectFavoriteByMemberId(Favorite favor) throws Exception {
		
		
		List<Favorite> result = null;

        try {
            result = sqlSession.selectList("FavoriteMapper.selectFavoriteByMemberId", favor);
         
            
        } catch (Exception e) {
        	e.printStackTrace();
            throw new Exception("즐겨찾기 목록 조회에 실패했습니다."); // 사용자에게 표시될 메시지를 설정한다.
            
        }
		
		return result;
	}


	@Override
	public int favoriteCount(Favorite favor) throws Exception {
		
		int result = 0;
        
        try {
            // 해당 멤버의 즐겨찾기 수를 카운트한다.
        	result = sqlSession.selectOne("FavoriteMapper.selectFavoriteByMemberIdCount", favor);            
            
        } catch (Exception e) {            
            throw new Exception("즐겨찾기 조회에 실패했습니다.");
        } // End try~catch
        
        
		return result;
	}



	@Override
	public void deleteFavoriteViaMypage(Favorite favor) throws Exception {
		
		
		try {
            sqlSession.delete("FavoriteMapper.deleteFavoriteViaMypage", favor);
         
            
        } catch (NullPointerException e) {

            throw new Exception("입력된 즐겨찾기가 없습니다.");
        } catch (Exception e) {

                throw new Exception("즐겨찾기에 실패했습니다.");
        }
		
	}



	@Override
	public void updateFavoriteByMemberOut(Favorite favor) throws Exception {
		
		try {
			sqlSession.update("FavoriteMapper.updateFavoriteByMemberOut", favor);
			
		} catch (Exception e) {
			throw new Exception("찜리스트 멤버 Null처리에 실패했습니다.");
		}		
		
	}



	@Override
	public int FavoriteCountByOthers(Favorite favor) throws Exception {
		int result = 0;
        
        try {
            // 해당 멤버의 게시물에 대한 즐겨찾기 수를 카운트한다.
        	result = sqlSession.selectOne("FavoriteMapper.FavoriteCountByOthers", favor);            
            
        } catch (Exception e) {            
            throw new Exception("게시물 즐겨찾기 조회에 실패했습니다.");
        } // End try~catch
        
        
		return result;
	}
	
	

	
	
	
	

}
