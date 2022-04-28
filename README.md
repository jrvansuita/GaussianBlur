<!-- Library Logo -->
<img src="https://github.com/jrvansuita/GaussianBlur/blob/master/app/src/main/res/mipmap-xxxhdpi/ic_launcher.png?raw=true" align="left" hspace="1" vspace="1">


<a href='https://ko-fi.com/A406JCM' target='_blank' align="right"><img align="right" height='36' src='https://az743702.vo.msecnd.net/cdn/kofi4.png?v=f'/></a><a href='https://play.google.com/store/apps/details?id=com.vansuita.gaussianblur.sample&pcampaignid=MKT-Other-global-all-co-prtnr-py-PartBadge-Mar2515-1' target='_blank' align="right"><img align="right" height='45' src='https://play.google.com/intl/en_us/badges/static/images/badges/en_badge_web_generic.png' alt='Get it on Google Play' /></a>
# Gaussian Blur



This is an [**Android**](https://developer.android.com) project. Easy and simple library to apply gaussian blur filter on images. The library lets you apply a gaussian blur filter on any images very fast because the image will be scaled down before apply the filter. Doing it asynchronous or not.

</br>


[![JitPak](https://jitpack.io/v/jrvansuita/GaussianBlur.svg)](https://jitpack.io/#jrvansuita/GaussianBlur)
[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-GaussianBlur-green.svg?style=true)](https://android-arsenal.com/details/1/4640) 
[![MaterialUp](https://img.shields.io/badge/MaterialUp-GaussianBlur-6ad0d9.svg?)](https://www.uplabs.com/posts/gaussianblur)


# Sample app
 Please check the sample app and feel free to help with a pull request. It's [located here](/app/).

<img src="images/mockups/allosaurus_nexus6p-portrait.png" height='auto' width='210'/><img src="images/mockups/triceratops_nexus6p-portrait.png" height='auto' width='210'/><img src="images/mockups/brontosaurus_nexus6p-portrait.png" height='auto' width='210'/><img src="images/mockups/velociraptor_nexus6p-portrait.png" height='auto' width='210'/>

 [![Appetize.io](https://img.shields.io/badge/Apptize.io-Run%20Now-brightgreen.svg?)](https://appetize.io/embed/uvqk1ee5m2pw1genqtayncfw70?device=nexus7&scale=50&autoplay=true&orientation=portrait&deviceColor=black) [![Demo](https://img.shields.io/badge/Demo-Download-blue.svg)](http://apk-dl.com/dl/com.vansuita.gaussianblur.sample) 
 [![Codacy Badge](https://api.codacy.com/project/badge/Grade/3fd61fd7128145008894a8cec0d1f8fc)](https://www.codacy.com/app/jrvansuita/GaussianBlur?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=jrvansuita/GaussianBlur&amp;utm_campaign=Badge_Grade)
<a target="_blank" href="https://developer.android.com/reference/android/os/Build.VERSION_CODES.html#GINGERBREAD"><img src="https://img.shields.io/badge/API-9%2B-blue.svg?style=flat" alt="API" /></a> 

# Setup

#### Step #1. Add the JitPack repository to your build file:

```gradle
allprojects {
    repositories {
        ...
        maven { url "https://jitpack.io" }
    }
}
```

#### Step #2. Add the dependency ([See latest release](https://jitpack.io/#jrvansuita/GaussianBlur)).

```groovy
dependencies {
    compile 'com.github.jrvansuita:GaussianBlur:+'
}
```

#### Step #3. Add the below lines on app module build.gradle file.

```groovy
defaultConfig {
    ...
    renderscriptTargetApi 19
    renderscriptSupportModeEnabled true
}
```

# Implementation

```java
//Synchronous blur
Bitmap blurredBitmap = GaussianBlur.with(context).render(R.mipmap.your_image);
imageView.setImageBitmap(blurredBitmap);
   
//Asynchronous blur
GaussianBlur.with(context).put(R.mipmap.your_image, imageView);

//Asynchronous with scaleDown and changing radius
GaussianBlur.with(context).size(300).radius(10).put(R.mipmap.your_image, imageView);
 ```
 
#

<a href="https://www.instagram.com/jnrvans/" target="_blank">
  <img src="https://camo.githubusercontent.com/c9dacf0f25a1489fdbc6c0d2b41cda58b77fa210a13a886d6f99e027adfbd358/68747470733a2f2f6564656e742e6769746875622e696f2f537570657254696e7949636f6e732f696d616765732f7376672f696e7374616772616d2e737667" alt="Instagram" witdh="44" height="44" hspace="10">
</a>
<a href="https://github.com/jrvansuita" target="_blank">
  <img src="https://camo.githubusercontent.com/b079fe922f00c4b86f1b724fbc2e8141c468794ce8adbc9b7456e5e1ad09c622/68747470733a2f2f6564656e742e6769746875622e696f2f537570657254696e7949636f6e732f696d616765732f7376672f6769746875622e737667" alt="Github" witdh="44" height="44" hspace="10">
</a>
<a href="https://play.google.com/store/apps/dev?id=8002078663318221363" target="_blank">
  <img src="https://camo.githubusercontent.com/8ce12185c778e13eed2073e7a6aba042ce5092d4d41744e7052e0fc16363c386/68747470733a2f2f6564656e742e6769746875622e696f2f537570657254696e7949636f6e732f696d616765732f7376672f676f6f676c655f706c61792e737667" alt="Google Play Store" witdh="44" height="44" hspace="10">
</a>
<a href="mailto:vansuita.jr@gmail.com" target="_blank" >
  <img src="https://camo.githubusercontent.com/4a3dd8d10a27c272fd04b2ce8ed1a130606f95ea6a76b5e19ce8b642faa18c27/68747470733a2f2f6564656e742e6769746875622e696f2f537570657254696e7949636f6e732f696d616765732f7376672f676d61696c2e737667" alt="E-mail" witdh="44" height="44" hspace="10">
</a>
