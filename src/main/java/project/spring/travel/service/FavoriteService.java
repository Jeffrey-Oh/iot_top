package project.spring.travel.service;

import java.util.List;

import project.spring.travel.model.Favorite;

public interface FavoriteService {
	/**
	 * 즐겨찾기에 등록하는 서비스
	 * @param favor
	 * @throws Exception
	 */
	public void addFavorite(Favorite favor) throws Exception;
	/**
	 * 즐겨찾기를 삭제하는 서비스
	 * @param favor
	 * @throws Exception
	 */
	public void deleteFavorite(Favorite favor) throws Exception;
	/**
	 * 즐겨찾기가 있는지 여부를 판단해 없으면 0 있으면 1을 리턴
	 * @param favor
	 * @return 0 아니면 1을 리턴
	 * @throws Exception
	 */
	public int favoriteExist(Favorite favor) throws Exception;
	/**
	 * 로그인한 회원의 즐겨찾기 목록을 불러온다.
	 * @param favor
	 * @return
	 * @throws Exception
	 */
	public List<Favorite> selectFavoriteByMemberId(Favorite favor) throws Exception;
	
	/**
	 * 로그인한 회원의 즐겨찾기 총 개수를 세서 리턴한다.
	 * @param favor
	 * @return 즐겨찾기 총 개수
	 * @throws Exception
	 */
	public int favoriteCount(Favorite favor) throws Exception;
	
	
	/**
	 * 마이페이지에서 즐겨찾기를 삭제할 때 쓰이는 메서드
	 * @param favor
	 * @throws Exception
	 */
	public void deleteFavoriteViaMypage(Favorite favor) throws Exception;
	
	/**
	 * 회원탈퇴 시 memberId를 null처리 하는 메서드
	 * @param favor
	 * @throws Exception
	 */
	public void updateFavoriteByMemberOut(Favorite favor) throws Exception;
	
	
	public int FavoriteCountByOthers(Favorite favor) throws Exception;
}
