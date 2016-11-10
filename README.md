
[![](https://jitpack.io/v/jrvansuita/GaussianBlur.svg)](https://jitpack.io/#jrvansuita/GaussianBlur)
[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-GaussianBlur-green.svg?style=true)](https://android-arsenal.com/details/1/4573)


# GaussianBlur
A easy and simple library to apply Gaussian blur on images. 


# Porpouse
This class lets you apply a fast Gaussian Blur on any images. A larger image will be scaled down to take menus time to apply the filter. You can also do it asynchronous or synchronous.


# Usage

#### Step 1. Add the JitPack repository to your build file:

    allprojects {
		repositories {
			...
			maven { url "https://jitpack.io" }
		}
	}

#### Step 2. Add the dependency

    dependencies {
	        compile 'com.github.jrvansuita:GaussianBlur:v1.0.0'
	}

# Samples
 You can take a look at the sample app [located on this project](/app/).

# Implementation

    //Synchronous
    Bitmap blurredBitmap = GaussianBlur.with(context).radius(25).noScaleDown(true).render(R.mipmap.your_image);
    imageView.setImageBitmap(blurredBitmap);
    
    //Synchronous - Only scaleDown
    Bitmap scaledDownBitmap = GaussianBlur.with(context).maxSixe(50).scaleDown(R.mipmap.your_image);
    imageView.setImageBitmap(scaledDownBitmap);
    
    //Asynchronous
    GaussianBlur.with(context).maxSixe(400).radius(25).put(R.mipmap.your_image, imageView);
