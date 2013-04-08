<?xml version="1.0" encoding="UTF-8"?>
<tileset name="forest" tilewidth="32" tileheight="32">
 <image source="tileset.png" width="128" height="128"/>
 <tile id="1">
  <properties>
   <property name="type" value="player"/>
  </properties>
 </tile>
 <tile id="4">
  <properties>
   <property name="type" value="wall"/>
  </properties>
 </tile>
 <tile id="5">
  <properties>
   <property name="enemyType" value="ent"/>
   <property name="type" value="enemy"/>
  </properties>
 </tile>
 <tile id="8">
  <properties>
   <property name="type" value="wall"/>
  </properties>
 </tile>
 <tile id="9">
  <properties>
   <property name="endpoint" value="first"/>
   <property name="flipped" value="false"/>
   <property name="slope" value="2"/>
   <property name="type" value="slope"/>
  </properties>
 </tile>
 <tile id="10">
  <properties>
   <property name="endpoint" value="second"/>
   <property name="flipped" value="false"/>
   <property name="slope" value="2"/>
   <property name="type" value="slope"/>
  </properties>
 </tile>
 <tile id="12">
  <properties>
   <property name="type" value="goal"/>
  </properties>
 </tile>
</tileset>