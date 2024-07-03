package LikeLion.backend.domain.board.controller;

import LikeLion.backend.domain.board.domain.entity.Board;
import LikeLion.backend.domain.board.dto.BoardRequest;
import LikeLion.backend.domain.board.dto.BoardResponse;
import LikeLion.backend.domain.board.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class BoardController {

    @Autowired
    private BoardService boardService;

    // create board rest api
    @PostMapping("/boards")
    public BoardResponse createBoard(@RequestBody BoardRequest boardRequest) {
        return boardService.createBoard(boardRequest);
    }

    // list all boards
    @GetMapping("/boards")
    public List<BoardResponse> listAllBoards() {
        return boardService.listAllBoards().stream()
                .map(board -> new BoardResponse(
                        board.getId(),
                        board.getTitle(),
                        board.getContent(),
                        board.getWriter(),
                        board.getViewCnt(),
                        board.getLikeCnt()))
                .collect(Collectors.toList());
    }

    // get board by id
    @GetMapping("/boards/{id}")
    public ResponseEntity<BoardResponse> getBoardById(@PathVariable Integer id) {
        Board board = boardService.getBoardById(id);
        BoardResponse boardResponse = new BoardResponse(
                board.getId(),
                board.getTitle(),
                board.getContent(),
                board.getWriter(),
                board.getViewCnt(),
                board.getLikeCnt());
        return ResponseEntity.ok(boardResponse);
    }

    // update board
    @PutMapping("/boards/{id}")
    public ResponseEntity<BoardResponse> updateBoard(@PathVariable Integer id, @RequestBody BoardRequest boardRequest) {
        Board board = boardService.updateBoard(id, boardRequest);
        BoardResponse boardResponse = new BoardResponse(
                board.getId(),
                board.getTitle(),
                board.getContent(),
                board.getWriter(),
                board.getViewCnt(),
                board.getLikeCnt());
        return ResponseEntity.ok(boardResponse);
    }

    // delete board
    @DeleteMapping("/boards/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteBoard(@PathVariable Integer id) {
        return boardService.deleteBoard(id);
    }
}
