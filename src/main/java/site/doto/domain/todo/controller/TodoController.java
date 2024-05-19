package site.doto.domain.todo.controller;

import org.springframework.web.bind.annotation.*;
import site.doto.domain.todo.dto.*;
import site.doto.global.dto.ResponseDto;

import java.util.ArrayList;
import java.util.List;

import static site.doto.global.status_code.SuccessCode.*;

@RestController
@RequestMapping("/todo")
public class TodoController {
    @PostMapping
    public ResponseDto<TodoDetailsRes> todoAdd(
            @RequestBody TodoAddReq todoAddReq
    ) {
        TodoDetailsRes result = TodoDetailsRes.builder()
                .id(10001L)
                .contents(todoAddReq.getContents())
                .date("2024-05-19 00:00:00")
                .isDone(false)
                .build();

        return ResponseDto.success(TODO_CRATED, result);
    }

    @GetMapping("/{memberId}")
    public  ResponseDto<TodoListRes> todoList(
            @PathVariable long memberId,
            @RequestBody TodoListReq todoListReq
    ) {
        List<TodoDetailsRes> todoDetailsResList = new ArrayList<>();

        for(int i = 1; i <= 10; i++) {
            todoDetailsResList.add(TodoDetailsRes.builder()
                    .id(10001L + i)
                    .contents("Mock Todo" + i)
                    .date("2024-05-19 00:00:00")
                    .isDone(false)
                    .build());
        }

        TodoListRes result = new TodoListRes();
        result.setTodoList(todoDetailsResList);

        return ResponseDto.success(TODO_INQUIRY_OK, result);
    }

    @PatchMapping("/{todoId}")
    public ResponseDto<TodoDetailsRes> todoModify(
            @PathVariable long todoId,
            @RequestBody TodoModifyReq todoModifyReq
    ) {
        TodoDetailsRes result = TodoDetailsRes.builder()
                .id(todoId)
                .contents("Modified Mock Todo")
                .date("2024-05-19 00:00:00")
                .isDone(false)
                .build();

        return ResponseDto.success(TODO_MODIFY_OK, result);
    }

    @DeleteMapping("/{todoId}")
    public ResponseDto<?> todoRemove(
            @PathVariable long todoId
    ) {
        return ResponseDto.success(TODO_DELETED, null);
    }

    @PatchMapping("/check/{todoId}")
    public ResponseDto<TodoDetailsRes> todoChangeDone(
            @PathVariable long todoId
    ) {
        TodoDetailsRes result = TodoDetailsRes.builder()
                .id(todoId)
                .contents("Modified Done Mock Todo")
                .date("2024-05-19 00:00:00")
                .isDone(false)
                .build();

        return ResponseDto.success(TODO_CHECK_OK, result);
    }

    @PostMapping("/date")
    public ResponseDto<TodoDetailsRes> todoRedo(
            @RequestBody TodoRedoReq todoRedoReq
    ) {
        return ResponseDto.success(TODO_RE_CREATED, null);
    }
}
