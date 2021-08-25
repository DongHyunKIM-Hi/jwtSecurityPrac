## Spring Seucurity 아키텍쳐

![](https://images.velog.io/images/hyundong_kk/post/38c2608a-ec7b-47e9-934e-a7d6d47e9c53/image.png)

#### 스프링 흐름

![](https://images.velog.io/images/hyundong_kk/post/d87a624f-3582-4bcc-b4f4-9c4867b6cd6d/image.png)


Request 요청이 들어오면 우선 Dispatch Servlet에서 맞는 controller에 값을 전달하주는 방식으로 서비스가 운영이 된다.

여기서 스프링 security는 1단계에 해당하는 Request에서 Dispatch Servlet으로 전달하기 전에 동작한다.

Security 검증 과정을 모두 통과한 후에 Dispatch Servlet으로 전달되는 것이다.

## AuthenticationFilter

authenticationFilter에서는 Request로 전달 받은 값을 인증하고 어떤 권한들이 들어있는지를 확인하는 과정이다. 

나는 JWT를 기준으로 인증과정을 진행하였으니 JWT를 열어서 값을 비교하고 인증하는 단계를 모아놓은 구조이다.


https://siyoon210.tistory.com/32 여기에 들어가보면 다양한 필터들이 있다는 것을 확인 할 수 있다. 여기서 필요한 필터를 등록해서 사용하면 되는 것이다.


## UsernamePasswordAuthetication Token

이 구조는 request 요청을 통해서 토큰을 받으면 AutheticationFilter에서 해당 토큰을 복호화하여 값을 읽을 수 있게 변환해서 저장하는 공간이다. 

![](https://images.velog.io/images/hyundong_kk/post/aa81c1f0-2fd5-4daf-8bbb-183430735cb4/image.png)

해당 코드를 보면 전달받은 토큰을 열어서 사용자 이름을 추출하고 해당 이름을 기준으로 UsernamePasswordAuthenticationToken에 새로운 객체를 생성했다.

## AuthenticationManager && Provider Manager

AuthenticationManager는 기술 명세서이고 이를 구현한 구현체가 Provider Manager이다.

해당구간은 복호화한 토큰= UsernamePasswordAuthenticationToken에 들어있는 값을 가지고 토큰의 유효성을 검사하는 단계이다.

토큰에 사용자 이름은 잘 들어있는지(사실 이건 UsernamePasswordAuthenticationToken 생성하면서 이미 검사하긴함), 토큰이 만료되지는 않았는지를 검사하는 단계이다.

![](https://images.velog.io/images/hyundong_kk/post/364b9921-88d8-4310-a9b9-25112a77248f/image.png)


이렇게 어떤 검사를 진행할 것인지를 메서드로 가지고 있는다.

위 코드를 기준으로는 토큰이 잘 들어있는지 검사하는 부분과 토큰의 유효성을 검사하는 코드가 보일 것이다. 해당 토큰을 검사하고 토큰에서 어떤 권한을 가지고 있는지를 추출하는 코드가 보일것이다.


## Authentication Provider

해당구간은 실질적인 인증과정이 일어나는 단계이다.

Provider Manager에서 각각의 인증과정을 진행하는 Authentication Provider 메서드들을 호출하고 있다.

![](https://images.velog.io/images/hyundong_kk/post/2dfd4590-817c-4ce2-8a4e-b8092475e4e8/image.png)

![](https://images.velog.io/images/hyundong_kk/post/a02eddcd-9880-4ef3-a7f7-0478cb704f6b/image.png)

빨간색으로 표시한게 실질적인 인증과정이 진행되는 메서드(Authentication Provider)이다.


## UserDetailService, UserDetail

해당구간은 토큰에 들어있는 아이디가 디비에 저장된 값인지를 검사하기 위해 존재한다.

![](https://images.velog.io/images/hyundong_kk/post/043abdd3-66d7-4e55-9436-9d7a10e9357b/image.png)

코드를 보면 UserDetailService를 상속받아서 토큰에 들어있는 아이디가 실제 디비에 저장된 값인지를 비교하는 작업을 진행중이다.

## securityConfig

![](https://images.velog.io/images/hyundong_kk/post/88ce67bc-30cc-4d26-9d94-458cb5942d56/image.png)

언제 Security가 실행되는지 설정해주는 곳이다. 원하는 대로 값을 설정해서 가지고 있으면 된다. 

그리고 가장 중요한 것은 Spring Securiy에 Jwt인증을 제공하지 않아서 직접 JWT Filter을 생성하였고 생성한 Filter를 AuthenticationFilter에 등록해줘야 하는데 addFilterBefore를 통해서 UsernamePasswordAuthenticationFilter가 실행되기 전에 내가 생성한 JwtFilter를 실행하도록 등록해줌으로써 JWT인증을 통해서 Spring Security를 사용할수있게 되었다.



#### 자세한 코드는 

https://github.com/DongHyunKIM-Hi/jwtSecurityPrac
