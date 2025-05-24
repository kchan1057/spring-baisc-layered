package com.example.layered.repository;

import com.example.layered.dto.MemoResponseDto;
import com.example.layered.entity.Memo;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface MemoRepository {

    MemoResponseDto saveMemo(Memo memo);
    List<MemoResponseDto> findAllMemos();
    Optional<Memo> findMemoById(Long id);
    Memo findMemoByIdOrElseThrow(Long id);
    int updateMemo(Long id, String title, String contents);
    int updateTitle(Long id, String title);
    int deleteMemo(Long id);
}
