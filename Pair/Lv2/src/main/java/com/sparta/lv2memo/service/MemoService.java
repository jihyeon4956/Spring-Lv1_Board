package com.sparta.lv2memo.service;

import com.sparta.lv2memo.dto.MemoRequestDto;
import com.sparta.lv2memo.dto.MemoResponseDto;
import com.sparta.lv2memo.entity.Memo;
import com.sparta.lv2memo.entity.User;
import com.sparta.lv2memo.entity.UserRoleEnum;
import com.sparta.lv2memo.repository.PostRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class MemoService {
    private final PostRepository postRepository;

    public MemoService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public MemoResponseDto createMemo(MemoRequestDto requestDto, User user) {
        //RequestDto -> Entity
        Memo memo = postRepository.save(new Memo(requestDto, user));

        //Entity -> ResponseDto
        return new MemoResponseDto(memo);
    }


    public List<MemoResponseDto> getMemos(User user) {
        //DB 조회
        UserRoleEnum userRoleEnum = user.getRole();
        List<Memo> memoList;

        if(userRoleEnum == UserRoleEnum.USER) {
            memoList = postRepository.findAllByUserOrderByModifiedAtDesc(user);  // 유저권한, 현재 유저의 메모만 조회
        } else {
            memoList = postRepository.findAllByOrderByModifiedAtDesc();  // 관리자권한, 모든 메모 조회
        }
        return memoList.stream().map(MemoResponseDto::new).toList();
    }


    public MemoResponseDto getMemo(Long id, User user) {
        // 해당 메모가 DB에 존재하는지 확인
        Memo memo = findMemo(id);

        // 해당 메모의 작성자와 현재 로그인한 사용자를 비교하여 작성자가 같지 않으면 예외 발생
        if (!memo.getUser().getUsername().equals(user.getUsername())) {
            throw new IllegalArgumentException("해당 메모에 접근 권한이 없습니다.");
        }
        return new MemoResponseDto(memo);
    }




    @Transactional
    public ResponseEntity<String> updateMemo(Long id, MemoRequestDto requestDto, User user) {
        // 해당 메모가 DB에 존재하는지 확인
        Memo memo = findMemo(id);
        // 해당 메모의 작성자와 현재 로그인한 사용자를 비교하여 작성자가 같지 않으면 예외 발생
        if (!memo.getUser().getUsername().equals(user.getUsername())) {
            throw new IllegalArgumentException("해당 메모를 수정할 권한이 없습니다.");
        }
        // memo 수정
        memo.update(requestDto);
        // 수정할 때 비밀번호 검증
        if (memo.getUser().getPassword().equals(user.getPassword())) {
            // 수정 성공
            return ResponseEntity.ok("수정 성공!");
        } else {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
    }


    public ResponseEntity<String> deleteMemo(Long id, User user) {
        // 해당 메모가 DB에 존재하는지 확인
        Memo memo = findMemo(id);

        // 해당 메모의 작성자와 현재 로그인한 사용자를 비교하여 작성자가 같지 않으면 예외 발생
        if (!memo.getUser().getUsername().equals(user.getUsername())) {
            throw new IllegalArgumentException("해당 메모를 삭제할 권한이 없습니다.");
        }
        // memo 삭제
        postRepository.delete(memo);
        return ResponseEntity.status(HttpStatus.OK)
                .body("{\"status\": 200, \"msg\": \"삭제 성공\"}");
    }


    private Memo findMemo(Long id) {
        return postRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("선택한 메모는 존재하지 않습니다."));
    }


}