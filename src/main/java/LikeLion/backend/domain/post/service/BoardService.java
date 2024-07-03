package LikeLion.backend.domain.post.service;

import LikeLion.backend.domain.post.domain.entity.Board;
import LikeLion.backend.domain.post.domain.entity.User;
import LikeLion.backend.domain.post.domain.request.BoardRequest;
import LikeLion.backend.domain.post.domain.response.BoardResponse;
import LikeLion.backend.domain.post.repository.BoardRepository;
import LikeLion.backend.domain.post.repository.UserRepository;
import LikeLion.backend.global.exception.GlobalExceptionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BoardService {
    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private UserRepository userRepository;

    // create board rest api
    public BoardResponse createBoard(BoardRequest boardRequest) {
        User user = userRepository.findById(boardRequest.getUserId())
                .orElseThrow(() -> new GlobalExceptionHandler.ResourceNotFoundException("User not exist with id :" + boardRequest.getUserId()));
        Board board = new Board();
        board.setTitle(boardRequest.getTitle());
        board.setContent(boardRequest.getContent());
        board.setWriter(boardRequest.getWriter());
        board.setUser(user);
        board = boardRepository.save(board);
        return new BoardResponse(board.getId(), board.getTitle(), board.getContent(), board.getWriter(), board.getViewCnt(), board.getLikeCnt());
    }

    // list all boards
    public List<Board> listAllBoards() {
        return boardRepository.findAll();
    }

    // get board by id
    public Board getBoardById(Integer id) {
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new GlobalExceptionHandler.ResourceNotFoundException("Board not exist with id :" + id));

        int cnt = board.getViewCnt();
        board.setViewCnt(cnt + 1);
        boardRepository.save(board); // 조회 수 증가 후 저장

        return board;
    }

    // update board
    public Board updateBoard(Integer id, BoardRequest boardRequest) {
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new GlobalExceptionHandler.ResourceNotFoundException("Board not exist with id :" + id));

        User user = userRepository.findById(boardRequest.getUserId())
                .orElseThrow(() -> new GlobalExceptionHandler.ResourceNotFoundException("User not exist with id :" + boardRequest.getUserId()));

        board.setTitle(boardRequest.getTitle());
        board.setContent(boardRequest.getContent());
        board.setWriter(boardRequest.getWriter());
        board.setUser(user);
        return boardRepository.save(board);
    }

    // delete board
    public ResponseEntity<Map<String, Boolean>> deleteBoard(Integer id) {
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new GlobalExceptionHandler.ResourceNotFoundException("Board not exist with id :" + id));

        boardRepository.delete(board);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }

    // increase like count
    public Board increaseLikeCount(Integer id) {
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new GlobalExceptionHandler.ResourceNotFoundException("Board not exist with id :" + id));

        board.setLikeCnt(board.getLikeCnt() + 1);
        return boardRepository.save(board);
    }
}
