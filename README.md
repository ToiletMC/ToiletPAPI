# plugin-ToiletPAPI

为了更好的性能，抛弃 JavaScript！  
为什么要从 [ToiletCore](https://github.com/ToiletMC/ToiletCore) 中独立出来？因为这样可以更好地使用 `/papi reload` 热加载。

### 占位符列表：

| 占位符                          | 功能               |
|------------------------------|------------------|
| %toilet_emoji_server_status% | 性能指示灯：🟢         |
| %toilet_emoji_world%         | 世界emoji：⛏️       |
| %toilet_emoji_worldtime%     | 世界时间emoji：🔆     |
| %toilet_info_worldtime%      | 世界时间信息： 🔆 12:00 |

### TODO

- [ ] 需要制作一个称号是否显示的占位符，还有一个称号占位符，用来管理称号，目前仍然在使用 JavaScript。
- [ ] 其余的占位符也需要迁移到 ToiletPAPI：%player_role% %time_info_of_resource_world% %mixed_playername%
- [ ] tps_title 和 scoreboard_textcolor 会让变量变得异常复杂！暂时不考虑。
  

