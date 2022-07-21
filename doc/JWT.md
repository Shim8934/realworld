# JWT 정리

## 참고 링크

1. [https://meetup.toast.com/posts/239](https://meetup.toast.com/posts/239)
2. [https://velopert.com/2389](https://velopert.com/2389)
3. [https://jwt.io/](https://jwt.io/)

## 구성

### 1. [헤더 (Header)](#Header)
### 2. [내용 (=정보, (Payload))](#PayLoad)
### 3. [서명 (Signature)](#Signature)

<br/>

#### 1. 헤더 <a id="Header"></a>

   1) 토큰의 타입 = JWT
   
   2) 해싱 알고리즘 정의
   - 토큰을 검증할 때 사용하는 signature에서 사용되는 알고리즘 정보를 여기에 담고 있다. (ex - HS256)

```json
{
    "type": "JWT",
    "alg": "HS256"
}
```
<br/>

#### 2. 정보 (Payload)  <a id="PayLoad"></a>
- 토큰에 담을 정보가 들어있다. 정보의 한 ‘단위’ 를 클레임(claim)이라 지칭하며, 클레임은 name / value 한 쌍으로 이뤄진다. 여러 개의 클레임을 넣을 수 있다.
- 크게 세 가지 종류의 클레임으로 나눌 수 있다.

    [(1) 등록된 클레임 (registered)](#Registered_Claim)

    [(2) 공개 클레임](#Public_Claim)

    [(3) 비공개 클레임](#Private_Claim)

    <br/>


2-1) 등록된 클레임 (registered)  <a id="Registered_Claim"></a>

   - 토큰에 대한 정보를 담기 위해 이름이 이미 정해진 클레임. 사용은 모두 선택적(optional)이다.
   
  | 종류 | 설명 |
  | :--- | :--- |
  | iss | 토큰 발급자 ( issuer ) |
  | sub | 토큰 제목 ( subject ) |
  | aud | 토큰 대상자 ( audience ) |
  | exp | 토큰의 만료시간 ( expiration ) 시간은 NumericDate 형식으로 되어있어야 한다. (ex: 1480849147370)<br />언제나 현재 시간보다 이후로 설정되어 있어야 한다. |
  | nbf | Not Before를 의미하며, 토큰의 활성 날짜와 비슷한 개념이다.<br />여기에도 NumericDate 형식으로 날짜를 지정하며, 이 날짜가 지나기 전까지는 토큰이 처리되지 않는다. |
  | iat | 토큰이 발급된 시간 ( issued at ) 이 값을 사용해 토큰의 age가 얼마나 되었는지 판단한다. |
  | jti | JWT의 고유 식별자로, 주로 중복 처리를 방지하기 위해 사용한다. 일회성 토큰에 사용할 때 유용한 클레임이다. |

<br/>

2-2) 공개 클레임 (Public)  <a id="Public_Claim"></a>

- 충돌을 방지하기 위해 URI 형식으로 이름을 짓는 클레임이다.
     
      ```json
        {
          "https://shim8934.github.io/jwt/is_Admin": true
        }
      ```
<br/>

2-3) 비공개 클레임 (Private)  <a id="Private_Claim"></a>

- 클라이언트와 서버 간의 협의 하에 사용하는 클레임이다.
     
      ```json
      {
          "username": "shim"
      }
      ```
   <br/>
3.서명 (Sigrnature)  <a id="Signature"></a>

- (1)헤더와 (2)내용(정보)의 인코딩 값을 합친 후, 주어진 비밀키(`secret`)로 해쉬를 한 후, 이 해쉬를 `base64` 형태로 인코딩한다.
- 서명 과정에서의 핵심은 `base64` 형태로 인코딩하는 값은 단순 문자열이 아닌 `hex` 값을 인코딩한다는 것이다.