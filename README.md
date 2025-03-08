# Compose Calendar View

The repository hosts the open-source code of Android Compose Calendar library.

[![Lint](https://github.com/AndrewKuliahin96/compose-calendar-view/actions/workflows/lint.yml/badge.svg)](https://github.com/AndrewKuliahin96/compose-calendar-view/actions/workflows/lint.yml)
![Maven Central](https://img.shields.io/maven-central/v/com.kuliahin/compose.calendar-view)
[![GitHub release](https://img.shields.io/github/release/AndrewKuliahin96/compose-calendar-view.svg?maxAge=10)](https://github.com/AndrewKuliahin96/compose-calendar-view/releases)
![Kotlin](https://kotlin-version.aws.icerock.dev/kotlin-version?group=com.kuliahin&name=compose.calendar-view)

<div style="display: flex; justify-content: space-between;">
  <img src="pics/example_screenshot.webp" width="591" alt="Example Screenshot">
  <img src="pics/example_customization.webp" width="591" alt="Example Customization">
</div>

### Features

* 🌈 Customizable theme
* 📑 Based on Android Paging library
* 🧭 Horizontal and Vertical swipe direction
* 🎲 Single/multiple/range dates selection
* ⚡️ Static/dynamic weekdays
* 🈯️ Customizable locale
* 💠 Condition-based dates customization
* 📈 Month view/Week view (single line mode)
* 🌓 Dark mode - **Work in progress**
* 🎆 Calendar events - **Work in progress**

### System Requirements

* `compileSdk` API level 34
* `minSdk` API level 26 or higher

### Setup

Our [change log][changelog] has release history.

The latest release is available
on [Maven Central](https://search.maven.org/artifact/com.kuliahin/compose.calendar-view).

Add library with desired release version to your `build.gradle`:

```kotlin
implementation("com.kuliahin:compose.calendar-view:$latestVersion")
```

### Usage

You can find the relevant documentation for the library [here](docs/LIBRARY_USAGE.md).

### Contributing

Found a bug? feel free to fix it and send a pull request or  [open an issue](https://github.com/AndrewKuliahin96/compose-calendar-view/issues).

License
-------

```
Copyright 2024 Andrii Kuliahin

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
```

[changelog]: https://github.com/AndrewKuliahin96/compose-calendar-view/releases

### Support me

[!["Buy Me A Coffee"](https://www.buymeacoffee.com/assets/img/custom_images/orange_img.png)](https://www.buymeacoffee.com/andrewkuliahin)
