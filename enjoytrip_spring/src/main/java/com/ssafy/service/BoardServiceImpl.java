package com.ssafy.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ssafy.dto.BoardDto;
import com.ssafy.dto.PagedDto;
import com.ssafy.mapper.BoardMapper;

@Service
public class BoardServiceImpl implements BoardService {

	private BoardMapper boardMapper;
	
	BoardServiceImpl(BoardMapper boardMapper) {
		super();
		this.boardMapper = boardMapper;
	}  

    @Override
    public void writeArticle(BoardDto boardDto) throws Exception {
    	boardMapper.writeArticle(boardDto);
    }

    @Override
    public PagedDto<BoardDto> listArticle(int pageNo, int articlesPerPage) throws Exception {
        int offset = (pageNo - 1) * articlesPerPage;
        List<BoardDto> content = boardMapper.listArticle(offset, articlesPerPage);
        int totalElements = boardMapper.countArticles();
        return new PagedDto<>(content, pageNo, articlesPerPage, totalElements);
    }

    @Override
    public BoardDto getArticle(int articleNo) throws Exception {
    	updateHit(articleNo);
        return boardMapper.getArticle(articleNo);
    }

    @Override
    public void updateHit(int articleNo) throws Exception {
    	boardMapper.updateHit(articleNo);
    }

    @Override
    public void modifyArticle(BoardDto boardDto) throws Exception {
    	boardMapper.modifyArticle(boardDto);
    }

    @Override
    public void deleteArticle(int articleNo) throws Exception {
    	boardMapper.deleteArticle(articleNo);
    }

    @Override
    public PagedDto<BoardDto> searchArticle(int pageNo, int articlesPerPage, String searchType, String keyword) throws Exception {
        if (keyword.isBlank()) {
            return listArticle(pageNo, articlesPerPage);
        }
        System.out.println(searchType);
		System.out.println(keyword);
        int offset = (pageNo - 1) * articlesPerPage;
        List<BoardDto> content = boardMapper.searchArticle(offset, articlesPerPage, searchType, keyword);
        int totalElements = boardMapper.countSearchedArticles(searchType, keyword);
        return new PagedDto<>(content, pageNo, articlesPerPage, totalElements);
//        List<BoardDto> allArticles = listArticle(pageNo, articlesPerPage);
//        List<BoardDto> matchedArticles = new ArrayList<>();
//        for (BoardDto boardDto : allArticles) {
//            String title = boardDto.getTitle();
//            List<Integer> matchPositions = KMP.kmpSearch(title, keyword);
//            if (!matchPositions.isEmpty()) {
//                List<int[]> mergedPositions = new ArrayList<>();
//                int start = -1;
//                int end = -1;
//                for (int position : matchPositions) {
//                    if (start == -1) {
//                        start = position;
//                        end = position + keyword.length();
//                    } else if (position <= end) {
//                        end = Math.max(end, position + keyword.length());
//                    } else {
//                        mergedPositions.add(new int[] { start, end - start });
//                        start = position;
//                        end = position + keyword.length();
//                    }
//                }
//                if (start != -1) {
//                    mergedPositions.add(new int[] { start, end - start });
//                }
//                StringBuilder highlightedTitle = new StringBuilder();
//                int lastPos = 0;
//                for (int[] pos : mergedPositions) {
//                    highlightedTitle.append(title.substring(lastPos, pos[0])).append("<mark>")
//                            .append(title.substring(pos[0], pos[0] + pos[1])).append("</mark>");
//                    lastPos = pos[0] + pos[1];
//                }
//                highlightedTitle.append(title.substring(lastPos));
//                boardDto.setTitle(highlightedTitle.toString());
//                matchedArticles.add(boardDto);
//            }
//
//        }
//
//        return matchedArticles;
    }

}
