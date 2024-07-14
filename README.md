# ModUtils Lite
The only moderation solution you'll ever need

## About
ModUtils Lite is the ultimate solution for moderating your Minecraft server. Whether you run a small community SMP or a massive network with hundreds of players, MUL can help you run your server. Featuring not only utilities for staff members, but also player essentials (such as private messaging), convenient features like automatically being vanished on join, and a toggleable system that allows for user's rank colors to be fetched and used in messages if you're using LuckPerms.
You can use this for a single server or a network - however, for network functionality, Redis is required.

## Features
Staff & Admin Chat Channels
Vanish
TP & TP Here
Private Messaging (cross-server support coming soon) and SocialSpy [NOT YET IMPLEMENTED]

### Possible Features
These might be added in future releases, or in a paid version of this plugin.
- Staff Mode
- Staff Join, Leave, & Switch Messages
- Freeze (for screensharing) command

## Command List

__Implemented__
- /staffchat (/sc) - modutils.command.staffchat - also grants permission to see staff chat messages and use the message prefix
- /n /adminchat (/ac) - modutils.command.adminchat - also grants permission to see admin chat messages and use the message prefix
- /vanish (/v) - modutils.command.vanish - also grants permission to see vanished players
- /teleport (/tp) - modutils.command.tp
- /teleporthere (/tphere) - modutils.command.tphere

__Will Be Implemented__
- /msg (/message) - no permission required
- /r (/reply) - no permission required
- /socialspy (/msgspy) - modutils.command.socialspy

## Full Permission List
- modutils.command.staffchat - access to send and receive staff chat messages
- modutils.command.adminchat - access to send and receive admin chat messages
- modutils.command.tp - access to use /tp
- modutils.command.tphere - access to use /tphere
- modutils.alerts.teleport - access to view same-server alerts for /tp and /tphere
- modutils.alerts.vanish - access to view same-server alerts for /vanish
