<h1 align="center">Compose Quill</h1><br>
AIzaSyA6F2ql0igVrS1e-N2lLKKAbfSeJz8fJJk
<div align="center">
<a href="https://opensource.org/licenses/Apache-2.0"><img alt="License" src="https://img.shields.io/badge/License-Apache%202.0-blue.svg"/></a>
<a href="https://android-arsenal.com/api?level=21" rel="nofollow"><img alt="API" src="https://camo.githubusercontent.com/0eda703da08220e08354f624a3fc0023f10416a302565c69c3759bf6e0800d40/68747470733a2f2f696d672e736869656c64732e696f2f62616467652f4150492d32312532422d627269676874677265656e2e7376673f7374796c653d666c6174" data-canonical-src="https://img.shields.io/badge/API-21%2B-brightgreen.svg?style=flat" style="max-width: 100%;"></a>
<a href="https://github.com/the-best-is-best/"><img alt="Profile" src="https://img.shields.io/badge/github-%23181717.svg?&style=for-the-badge&logo=github&logoColor=white" height="20"/></a>
<a href="https://search.maven.org/search?q=g:%22com.mohamedrejeb.richeditor%22%20AND%20a:%22richeditor-compose%22"><img alt="Maven Central" src="https://img.shields.io/maven-central/v/io.github.the-best-is-best/composequill"/></a>
</div>

A Compose Quill library for both Jetpack Compose for android, fully customizable and supports the
common rich text editor features

- **Easy to use**: Compose Quill's API leverages Kotlin's language features for simplicity and
  minimal boilerplate.
- **WYSIWYG**: Compose Rich Editor is a WYSIWYG editor that supports the most common text styling
  features.
- **Future**: Support add image and video from gallery (support png in image and mp4 to video)

It depend on <a href= "https://github.com/MohamedRejeb/Compose-Rich-Editor/tree/main">
Compose-Rich-Editor </a>

## Download

[![Maven Central](https://img.shields.io/maven-central/v/io.github.the-best-is-best/composequill)](https://central.sonatype.com/artifact/io.github.the-best-is-best/composequill)

Compose Quill is available on `mavenCentral()`.

```kotlin
implementation("io.github.the-best-is-best:composequill:1.0.0-rc3")
```

## How to use

```kotlin
QuillEditor(
    modifier = Modifier
        .padding(0.dp)
        .background(Color.White),
    quillStates = quillStates,
  textRichToolBarStyle = TextRichToolBarStyle(
    iconColor = Color.Black,
    iconSelectedColor = Color.White,

    ),

  onChange = {
    Log.d("QuillEditor", "onCreate: $it")
  })
```

<br></br>

### Setup

#### You can replace or send data json to display

##### First use rememberQuillStates()

```kotlin
val quillStates = rememberQuillStates()
```

and use `quillStates1`

```kotlin
quillStates.sendData(json)
```

note json get it from onChange

<br></br>

### Style

#### quillStyle

use it for styling the quill parent of Toolbar and Editor

#### quillEditorStyle

use it for style editor typing

#### quillEditorToolBarStyle

use it for editor tool style

#### onChange

to receive json to save it

<br></br>

#### Features

- Can make `showImagePicker` = Boolean ,
  `showVideoPicker` = Boolean,

