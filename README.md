# MessageView

Prototype to display incoming message in a dialog. 

In project folder is a demo application named DemoMessageView. After starting this demo you see followed scene:

![DemoViewApp](https://github.com/MatFX/MessageView/blob/master/demoViewApp.png "DemoViewApp")

The button "open view" is to start the prototype dialog. The dialog is divided in two section. The left side is to show the incoming messages as a list.
On the right side you will seen the content of selected message. 
After clicking the button "start test", demo messages will be created and fill the prototype dialog. 

![ProtoTypeDialog](https://github.com/MatFX/MessageView/blob/master/filledDialog.png "ProtoTypeDialog")

The prototype dialog can hold in the ListView two different item elemtent types. The normal element is the MessageItem.
It is a simple container with the content, headline and kind of message. The extended element from MessageItem is the
ExpirationMessageItem. It is a element how removed automatically from ListView after X seconds.
After selecting a item from the list the content will be displayed on the rigt side. Furthermore, the user has the option of
delete the displayed message from the dialog.
When the user move the mouse pointer outer of the dialog, the right side shows always the last incoming message.

Remark: The two combo boxes at the DemoViewApp shown you the possibility to customize the dialog.








