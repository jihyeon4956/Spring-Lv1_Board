package com.sparta.lv2memo.controller;

import com.sparta.lv2memo.dto.MemoRequestDto;
import com.sparta.lv2memo.dto.MemoResponseDto;
import com.sparta.lv2memo.security.UserDetailsImpl;
import com.sparta.lv2memo.service.MemoService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class MemoController {

    private final MemoService memoService;

    public MemoController(MemoService memoService) {
        this.memoService = memoService;
    }

    //create
    @PostMapping("/posts")
    public MemoResponseDto createMemo(@RequestBody MemoRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return memoService.createMemo(requestDto, userDetails.getUser());
    }

    //readAll
    @GetMapping("/posts")
    public List<MemoResponseDto> getAllMemo(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return memoService.getMemos(userDetails.getUser());
    }


    //read
    @GetMapping("/post/{id}")
    public MemoResponseDto getMemo(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return memoService.getMemo(id, userDetails.getUser());
    }

    //update
    @PutMapping("/post/{id}")
    public ResponseEntity<String> updateMemo(@PathVariable Long id, @RequestBody MemoRequestDto requestDto
                                              , @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return memoService.updateMemo(id, requestDto, userDetails.getUser());

    }

    //delete
    @DeleteMapping("/post/{id}")
    public ResponseEntity<String> deleteMemo(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return memoService.deleteMemo(id, userDetails.getUser());
    }
}