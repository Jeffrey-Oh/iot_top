package project.spring.travel.service;

import project.spring.travel.model.CategoryLike;

public interface CategoryLikeService {
	/**
	 * 게시물 좋아요 + 1
	 * @MethodName - addCategoryLike
	 * @param categoryLike
	 * @throws Exception
	 * @author     - Administrator
	 * @lastUpdate - 2019. 4. 8.
	 */
	public void addCategoryLike(CategoryLike categoryLike) throws Exception;
	
	/**
	 * 게시물 좋아요 - 1
	 * @MethodName - deleteCategoryLike
	 * @param categoryLike
	 * @throws Exception
	 * @author     - Administrator
	 * @lastUpdate - 2019. 4. 8.
	 */
	public void deleteCategoryLike(CategoryLike categoryLike) throws Exception;
	
	/**
	 * 게시물 좋아요 현황 조회
	 * @MethodName - selectCategoryLike
	 * @param categoryLike
	 * @return
	 * @throws Exception
	 * @author     - Administrator
	 * @lastUpdate - 2019. 4. 8.
	 */
	public int selectCategoryLike(CategoryLike categoryLike) throws Exception;
	
	/**
	 * 특정 회원의 게시물 좋아요 참조관계 해제
	 * @MethodName - updateCategoryLikeMemberOut
	 * @param categoryLike
	 * @throws Exception
	 * @author     - Administrator
	 * @lastUpdate - 2019. 4. 9.
	 */
	public void updateCategoryLikeMemberOut(CategoryLike categoryLike) throws Exception;
	
	/**
	 * 등급제를 위한 카운트
	 * @MethodName - countByMemberId
	 * @param categoryLike
	 * @return
	 * @throws Exception
	 * @author     - JeffreyOh
	 * @lastUpdate - 2019. 4. 13.
	 */
	public int countByMemberId(CategoryLike categoryLike) throws Exception;
	
	/**
	 * 등급제를 위한 카운트 (사용자)
	 * @MethodName - countByUser
	 * @param categoryLike
	 * @return
	 * @throws Exception
	 * @author     - JeffreyOh
	 * @lastUpdate - 2019. 4. 13.
	 */
	public int countByUser(CategoryLike categoryLike) throws Exception;
	
	/**
	 * 게시물 삭제 시 게시물 좋아요 참조관계 삭제
	 * @MethodName - deleteCategoryLikeMemberOut
	 * @param categoryLike
	 * @throws Exception
	 * @author     - Administrator
	 * @lastUpdate - 2019. 4. 19.
	 */
	public void deleteCategoryLikeMemberOut(CategoryLike categoryLike) throws Exception;
}
