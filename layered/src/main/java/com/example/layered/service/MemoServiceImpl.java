package com.example.layered.service;

import com.example.layered.dto.MemoRequestDto;
import com.example.layered.dto.MemoResponseDto;
import com.example.layered.entity.Memo;
import com.example.layered.repository.MemoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;

@Service
public class MemoServiceImpl implements MemoService {

    private final MemoRepository memoRepository;

    public MemoServiceImpl(MemoRepository memoRepository) {
        this.memoRepository = memoRepository;
    }

    //MemoController랑 마찬가지로 여기서 Repository Layer로 이동하니 여기서 객체 생성
    @Override
    public MemoResponseDto saveMemo(MemoRequestDto dto) {

        //요청받은 데이터로 MEMO 객체 생성 ID 없음
        Memo memo = new Memo(dto.getTitle(), dto.getContents());

        //DB 저장
        Memo savedMemo = memoRepository.saveMemo(memo);
        return new MemoResponseDto(savedMemo);
    }

    @Override
    public List<MemoResponseDto> findAllMemo() {
        return memoRepository.findAllMemos();
    }

    @Override
    public MemoResponseDto findMemoById(Long id) {

        Memo memo = memoRepository.findMemoById(id);

        if (memo == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Does not exist id = " + id);
        }
        return new MemoResponseDto(memo);
    }

    @Override
    public MemoResponseDto updateMemo(Long id, String title, String contents) {

        Memo memo = memoRepository.findMemoById(id);

        if (memo == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Does not exist id = " + id);
        }

        if(title == null || contents == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The title and content are required values.");
        }

        memo.update(title, contents);
        return new MemoResponseDto(memo);
    }

    @Override
    public MemoResponseDto updateTitle(Long id, String title, String contents) {
        Memo memo = memoRepository.findMemoById(id);

        if(memo == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Does not exist id = " + id);
        }
        if(title == null || contents != null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The title is a required value.");
        }
        memo.updateTitle(title);
        return new MemoResponseDto(memo);
    }

    @Override
    public void deleteMemo(Long id) {

        Memo memo = memoRepository.findMemoById(id);

        if(memo == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Does not exist id: " + id);
        }
        memoRepository.deleteMemo(id);
    }
}
