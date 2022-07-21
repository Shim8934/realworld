## Security 인증 처리 정리 
### 1. 인증(Authentication) 핵심 컴포넌트
1. UsernamePasswordAuthenticationFilter (기본 인증필터)
   * 사용자 인증 요청을 Authentication 인터페이스로 추상화하고, AuthenticationManager를 호출.
   * Authentication 인터페이스에서 제공하는 핵심 메소드
     ```text
     1. Object getPrincipal() - 인증 아이디
     2. Object getCredentials() - 인증 비밀번호
     3. Collection<? extends GrantedAuthority> getAuthorities() - 인증된 사용자의 권한 목록
     4. Object getDetails() - 인증된 사용자의 부가 정보
     ```
2. AuthenticationManager
   * 사용자 아이디/비밀번호를 인증하기 위해 적절한 AuthenticationProvider를 찾아 처리를 위임.  

3. AuthenticationProvider  
   * 실질적으로 사용자 인증을 처리하고, 인증 결과를 Authentication 인터페이스로 반환. 
  
### 2. 핵심 컴포넌트 커스텀
 | 인증 인터페이스 | 사용 기본 구현체 | 커스텀 구현체 |
 | :---: | :---: | :---: |
 | UsernamePasswordAuthenticationFilter | UsernamePasswordAuthenticationFilter | AuthenticationRestController |
 | AuthenticationManager | UsernamePasswordAuthenticationToken | JwtAuthenticationToken |
 | AuthenticationProvider | DaoAuthenticationProvider | JwtAuthenticationProvider |

1. AuthenticationRestController (UsernamePasswordAuthenticationFilter)
    * HTTP 요청에서 사용자 ID, 비밀번호 추출
    * JwtAuthenticationToken을 생성해, AuthenticationManager를 호출
  
2. JwtAuthenticationToken(UsernamePasswordAuthenticationToken)
    * 사용자 인증 요청을 추상화한 Authentication 인터페이스 구현체
    * Principal 멤버 변수는 2개의 의미로 사용
      1. 인증 전: 사용자 ID
      2. 인증 후: JwtAuthentication (사용자 PK, 이메일)
  
3. JwtAuthenticationProvider(DaoAuthenticationProvider)
    * UserService를 통해 사용자 정보를 DB에서 조회
    * 실질적인 사용자 인증 처리 로직을 수행 및 JWT 생성
    * 인증 결과는 JwtAuthenticationToken 타입으로 반환
  
4. JwtAuthenticationTokenFilter
    * 필터 체인에서 UsernamePasswordAuthenticationFilter 앞에 위치
    * HTTP요청에서 JWT 추출하고 유효성 검증
    * JWT 인증 결과는 JwtAuthenticationToken 타입으로 SecurityContextHolder에 저장됨
      * REST 컨트롤러의 @AuthenticationPrincipal 어노테이션은 JwtAuthenticationToken.principal을 참조함
      * JwtAuthenticationToken.principal은 JwtAuthentication와 동일
  

---
## Security 사용한 설정사항 관련 정리

### 1. 2022년 2월 11일 자로 WebSecurityConfigureAdapter Deprecated
> [spring.io 블로그](https://spring.io/blog/2022/02/21/spring-security-without-the-websecurityconfigureradapter)

1) HttpSecurity http configure를 공식 블로그에서 사용한 방식으로 설정
2) AuthenticationManager Bean 설정을 AuthenticationConfiguration에서 얻어오는 방식으로 사용

### 2. httpSecurity 설정 관련

1) CORS (Cross-Origin Resource Sharing) - [Realworld CORS Spec](https://realworld-docs.netlify.app/docs/specs/backend-specs/cors)  
(1) localhost:8080, swagger 허용  
(2) GET, POST, PUT, DELETE, OPTIONS 에 해당한 HTTP 요청만 허용  
(3) Authorization, Content-Type 헤더만 허용
  
2) CSRF (Cross-Site Request Forgey)
   * REST API 용도로만 RealWorld 프로젝트를 제작하기에 사용하지 않는 설정. (disabled)

3) FormLogin 미사용