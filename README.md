## detectsoftnav

Android 4.0 (API level 14) introduced the ability to hide/show the soft
navigation bar. This fact is not accounted for by React Native, which will
happily continue to show portions of Views underneath it when it is shown.

This package allows you to add listeners for the soft navigation bar being
hidden and shown and modify your View layout accordingly. It also allows you
to check the current status of the soft navigation bar at any given time.

### Installation

Download via NPM

```shell
npm i -D rn-detect-soft-nav
```

Link, either via `react-native link` or manually

```shell
react-native link rn-detect-soft-nav
```

**-- OR --**

```java
// android/settings.gradle
// add the following at the end of the file
include ':rn-detect-soft-nav'
project(':rn-detect-soft-nav').projectDir =
  new File(rootProject.projectDir, '../node_modules/rn-detect-soft-nav/android')

// android/app/build.gradle
dependencies {
  ...
  compile project(':rn-detect-soft-nav') // <-- add this
}

// android/app/src/main/java/.../MainApplication.java
import pro.aaronross.util.DetectSoftNav; // <-- add this
...
@Override
protected List<ReactPackage> getPackages() {
  return Arrays.<~>asList(
    ...,
    new DetectSoftNavPackage()  // <-- and this
  );
}
```

### Hide/Show Listeners

```js
import DetectSoftNav from 'rn-detect-soft-nav'
...
DetectSoftNav.addListeners({
  onShown: () => {
    // navbar is locked to bottom of screen, add
    // extra padding to prevent occlusion
  },
  onHidden: () => {
    // navbar is hidden, remove extra padding
  }
})
```

### Get Current Visibility

```js
import DetectSoftNav from 'rn-detect-soft-nav'
...
DetectSoftNav.isVisible().then(v => { /* do stuff with v */ })
```

You can also use async/await, if you would prefer.

```js
import DetectSoftNav from 'rn-detect-soft-nav'
...
foo = async () => {
  const v = await DetectSoftNav.isVisible()
  // do stuff with v
}
```

### Recommended Use

For best performance/ease of use, I recommend implementing a View similar to
RN's built-in `SafeAreaView` with flexible footer padding to account for
hiding/showing the soft navbar, then replacing the base View for any screens
that care about navbar visiblity with that enhanced View.
