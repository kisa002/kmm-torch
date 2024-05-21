# kmm-torch
본 프로젝트는 Kotlin/Compose Multiplatform 두 가지 방식으로 만들어진 Android/iOS 토치앱 프로젝트입니다.<br>
관련 설명 자료를 원하신다면 아래의 블로그 포스팅을 통해 자세한 설명을 확인하실 수 있습니다.<br>
[코틀린 멀티플랫폼 Android & iOS 토치앱 만들기 - HolyKisa](https://holykisa.tistory.com/111)

# Multiplatform
## Compose Multiplatform
현재 보고 있는 브랜치의 최종 소스코드는 Compose Multiplatform으로 구현되어 있습니다.</br>
Android와 iOS 모두 같은 Compose UI를 사용하고 있습니다.

## Kotlin Multiplatform
Kotlin Multiplatform 방식으로 구현되어있는 소스코드를 확인하고 싶으시다면 아래의 브랜치에서 확인 가능합니다.</br>
[archive/kotlin-multiplatform](https://github.com/kisa002/kmm-torch/tree/archive/kotlin-multiplatform)

Compose/SwiftUI를 통해 각 UI를 구현하고 네이티브에서 TorchController를 제어하고 있습니다.

# TorchController
## Expect
- turnOn()
  - 토치를 활성화합니다.
- turnOff()
  - 토치를 비활성화합니다.
- isAvailable
  - 토치 사용 가능 여부를 반환합니다.
- isEnabledFlow
  - 현재 토치 활성화 여부를 Flow로 반환합니다.
## Android
`Android의 CameraManager를 사용합니다`
- turnOn/Off
  - CameraManager의 setTorchMode를 통해 torch를 제어합니다.
- isAvailable
  - CameraManager의 getCameraCharacteristics에서 CameraCharacteristics.FLASH_INFO_AVAILABLE를 가져와 사용가능 여부를 반환합니다.
- isEnabledFlow
  - callbackFlow 내 CameraManager.TorchCallback을 등록하여 상태 이벤트를 방출합니다.
## iOS
`iOS의 AVCaptureDevice를 사용합니다.`
- turnOn/Off
  - Device의 setTorchMode를 통해 torch를 제어합니다.
- isAvailable
  - Device의 isTorchAvailable를 통해 상태를 반환합니다.
- isEnabledFlow
  - callbackFlow 내 KVO로 옵저버를 등록하여 상태 이벤트를 방출합니다.
 
# Captures
|Android|iOS|
|-------|---|
|![Android Torch](https://github.com/kisa002/kmm-torch/assets/4679634/d151fa64-2d2e-4be9-803f-79198800129e)|![iOS Torch](https://github.com/kisa002/kmm-torch/assets/4679634/56c5d7d1-5d85-4407-9cd6-dfe342e7f4ca)|
