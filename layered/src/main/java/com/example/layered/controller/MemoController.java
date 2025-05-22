package com.example.layered.controller;

import com.example.layered.dto.MemoRequestDto;
import com.example.layered.dto.MemoResponseDto;
import com.example.layered.entity.Memo;
import com.example.layered.service.MemoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController // @Controller + @ResponseBody
@RequestMapping("/memos")
public class MemoController {

    //final로 생성을 했기 때문에 최초로 생성된 MemoService의 memoService가 프로그램이
    //종료될 때까지 계속 유지가 된다.
    private final MemoService memoService;
    //이 Layer에서 memoService로 전송이 되니 여기서 선언

    public MemoController(MemoService memoService) {
        this.memoService = memoService;
    }

    @PostMapping
    public ResponseEntity<MemoResponseDto> createMemo(@RequestBody MemoRequestDto dto) {

        //Service Layer를 호출, 응답
        return new ResponseEntity<>(memoService.saveMemo(dto), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<MemoResponseDto>> findAllMemos(){

        return new ResponseEntity<>(memoService.findAllMemo(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MemoResponseDto> findMemoById(@PathVariable Long id){

        return new ResponseEntity<>(memoService.findMemoById(id), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MemoResponseDto> updateMemo(
            @PathVariable Long id,
            @RequestBody MemoRequestDto dto){
        return new ResponseEntity<>(memoService.updateMemo(id, dto.getTitle(), dto.getContents()), HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<MemoResponseDto> updateTitle(
            @PathVariable Long id,
            @RequestBody MemoRequestDto dto
    ) {
        return new ResponseEntity<>(memoService.updateTitle(id, dto.getTitle(), dto.getContents()), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMemo(@PathVariable Long id){
        memoService.deleteMemo(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}