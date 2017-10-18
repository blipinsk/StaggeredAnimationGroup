StaggeredAnimationGroup
==================

[![License](https://img.shields.io/github/license/blipinsk/StaggeredAnimationGroup.svg?style=flat)](https://www.apache.org/licenses/LICENSE-2.0)

A `ConstraintLayout` group that allows for simple staggered animations.

![ ](/StaggeredAnimationGroup.gif)

Usage
=====
*For a working implementation of this library see the `sample/` folder.*

1. Add `StaggeredAnimationGroup` to your `ConstraintLayout`:

```xml
<android.support.constraint.ConstraintLayout
    android:layout_width="match_parent"
    android:layout_height="match_parent">

    <View
        android:id="@+id/firstView"
        android:layout_width="match_parent"
        android:layout_height="match_parent"> 
    
    <View
        android:id="@+id/secondView"
        android:layout_width="match_parent"
        android:layout_height="match_parent">
        
    <!-- here's the group -->
    <com.bartoszlipinski.constraint.StaggeredAnimationGroup
        android:id="@+id/group"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        app:constraint_referenced_ids="firstView, secondView"/>

</android.support.constraint.ConstraintLayout>
```

2. Obtain `StaggeredAnimationGroup` instance from the layout:

```java
StaggeredAnimationGroup group = findViewById(R.id.group);
```

2. Use proper methods to show and hide `Views` in your group (with ids: `firstView`, `secondView`)

```java
group.show();
// or
group.hide();
```

Including In Your Project
-------------------------
Add a proper dependency inside your `build.gradle`. Like this:

```xml
dependencies {
    implementation 'com.bartoszlipinski.constraint:staggeredanimationgroup:1.0.0'
    
    // obviously, you will also need those two:
    implementation 'com.android.support.constraint:constraint-layout:1.1.0-beta1'
    implementation 'com.android.support:transition:26.1.0'
}
```

Developed by
============
 * Bartosz Lipiński

License
=======

    Copyright 2017 Bartosz Lipiński

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
