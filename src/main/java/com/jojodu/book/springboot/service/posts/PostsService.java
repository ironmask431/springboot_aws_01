package com.jojodu.book.springboot.service.posts;

import com.jojodu.book.springboot.domain.post.Posts;
import com.jojodu.book.springboot.domain.post.PostsRepository;
import com.jojodu.book.springboot.web.dto.PostsResponseDto;
import com.jojodu.book.springboot.web.dto.PostUpdateRequestDto;
import com.jojodu.book.springboot.web.dto.PostsListResponseDto;
import com.jojodu.book.springboot.web.dto.PostsSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PostsService {
    private final PostsRepository postsRepository;

    //postsRepository 를 통해서 insert를 하고, pk id를 리턴받음
    public Long save(PostsSaveRequestDto requestDto){
        return postsRepository.save(requestDto.toEntity()).getId();
    }

    //update 메소드에 postsRepository를 통해 별도로 쿼리를 날리는 부분이없다.
    //이게 가능한 이유는 jpa의 영속성 컨텍스트 때문. 영속성 컨텍스트란 엔티티를 영구저장하는 환경
    //jpa의 엔티티매니저가 활성화된 상태로(spring data jpa를 쓴다면 기본옵션)
    //트랜잭션(@Transactional)안에서 db에서 데이터(Entity)를 가져오면 이 데이터는 영속성 컨텍스트가 유지된상태
    //이 상태에서 해당데이터의 값을 변경하면 트랜잭션이 끝나는 시점에 자동으로 update가 됨.
    //Entity 객체의 값만 변경하면 별도로 update 쿼리를 날릴 필요가없음.
    @Transactional
    public Long update(Long id, PostUpdateRequestDto requestDto){
        Posts posts = postsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id="+id));
        posts.update(requestDto.getTitle(), requestDto.getContent());
        return id;
    }

    @Transactional
    public void delete(Long id){
        Posts posts = postsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id="+id));
        postsRepository.delete(posts);
        //.deleteById(id) 로 id로도 삭제할 수 있음.
        //존재하는 Posts인지 먼저확인하기위해 findById()로 조회후 delete() 실행

    }

    public PostsResponseDto findById (Long id){
        Posts entity = postsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id="+id));
        return new PostsResponseDto(entity);
    }

    //@Transactional(readOnly = true) : readOnly = true를 추가하면 트랜잭션 범위는 추가하되,
    //조회기능만 남겨두어 조회속도가 개선됨. 등록,수정,삭제가 없는 서비스메소드에 사용하는것을 추천
    @Transactional(readOnly = true)
    public List<PostsListResponseDto> findAllDesc(){
        return postsRepository.findAllDesc().stream()
                .map(posts -> {return new PostsListResponseDto(posts);}).collect(Collectors.toList());
        //postsRepository의 결과 List<posts>의 stream 을 map을 통해 PostsListResponseDto 변환 -> List로 변환
        //.findAllDesc() 대신 .findAll() 를 사용해도 정상동작 (findAll()은 jpa기본 메소드)
    }


}
