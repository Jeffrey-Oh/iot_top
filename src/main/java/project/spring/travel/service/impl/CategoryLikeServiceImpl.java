package project.spring.travel.service.impl;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import project.spring.travel.model.CategoryLike;
import project.spring.travel.service.CategoryLikeService;

@Service
public class CategoryLikeServiceImpl implements CategoryLikeService{

	private static Logger logger = LoggerFactory.getLogger(CategoryLikeServiceImpl.class);
	
	@Autowired
	SqlSession sqlSession;

	@Override
	public void addCategoryLike(CategoryLike categoryLike) throws Exception {
		int result = 0;
		
		try {
			result = sqlSession.insert("CategoryLikeMapper.addCategoryLike", categoryLike);
			if (result == 0) {
				throw new NullPointerException();
			}
		} catch (NullPointerException e) {
			throw new Exception("게시물 좋아요 등록할 게시물이 없습니다.");
		} catch (Exception e) {
			throw new Exception("게시물 좋아요 추가 실패");
		}
		
	}

	@Override
	public void deleteCategoryLike(CategoryLike categoryLike) throws Exception {
		int result = 0;
		
		try {
			result = sqlSession.delete("CategoryLikeMapper.deleteCategoryLike", categoryLike);
			if (result == 0) {
				throw new NullPointerException();
			}
		} catch (NullPointerException e) {
			throw new Exception("게시물 좋아요 취소할 게시물이 없습니다.");
		} catch (Exception e) {
			throw new Exception("게시물 좋아요 취소 실패");
		}
	}

	@Override
	public int selectCategoryLike(CategoryLike categoryLike) throws Exception {
		int result = 0;
		
		try {
			// 좋아요 하기 전인 사람들은 데이터가 없으니 0에 대한 예외처리 하지않음
			result = sqlSession.selectOne("CategoryLikeMapper.selectCategoryLike", categoryLike);
		} catch (NullPointerException e) {
			throw new Exception("게시물이 없습니다.");
		} catch (Exception e) {
			throw new Exception("게시물 좋아요 현황 조회 실패");
		}
		return result;
	}

	@Override
	public void updateCategoryLikeMemberOut(CategoryLike categoryLike) throws Exception {
		try {
			sqlSession.update("CategoryLikeMapper.updateCategoryLikeMemberOut", categoryLike);
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
			throw new Exception("참조관계 해제에 실패했습니다.");
		}
	}

	@Override
	public int countByMemberId(CategoryLike categoryLike) throws Exception {
		int result = 0;
		
		try {
			// 좋아요 하기 전인 사람들은 데이터가 없으니 0에 대한 예외처리 하지않음
			result = sqlSession.selectOne("CategoryLikeMapper.CountByMemberId", categoryLike);
		} catch (NullPointerException e) {
			throw new Exception("게시물이 없습니다.");
		} catch (Exception e) {
			throw new Exception("게시물 사용자 좋아요 유지 조회 실패");
		}
		return result;
	}

	@Override
	public int countByUser(CategoryLike categoryLike) throws Exception {
		int result = 0;
		
		try {
			// 좋아요 하기 전인 사람들은 데이터가 없으니 0에 대한 예외처리 하지않음
			result = sqlSession.selectOne("CategoryLikeMapper.CountByUser", categoryLike);
		} catch (NullPointerException e) {
			throw new Exception("게시물이 없습니다.");
		} catch (Exception e) {
			throw new Exception("게시물 유저 좋아요 유지 조회 실패");
		}
		return result;
	}

	@Override
	public void deleteCategoryLikeMemberOut(CategoryLike categoryLike) throws Exception {
		try {
			sqlSession.delete("CategoryLikeMapper.deleteCategoryLikeMemberOut", categoryLike);
		} catch (Exception e) {
			throw new Exception("데이터 삭제에 실패했습니다.");
		}
	}

}
