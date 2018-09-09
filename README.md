# ProjectDoorAi
### It's Project for graduate Project 
### This md file is for What I do and study every day

## 2018_09_05
* Install raspbian
* Buy Mic for Raspberry Pi
____
## 2018_09_06
* Install raspbian 
* Setup MIC and speaker
* have ISSUE that speaker and MIC are not worked together If Speaker work, then MIC doesn't work and Contrary
____
## 2018_09_08
* Solve ISSUE about Speaker and MIC. It's problem that Set device ID is diffrent each other. 
In my case, USB MIC is card 0,device 0 and Speaker(AUX) is card 1 and device 0
so I solve it by making asoundrc
` pcm.!default{
	type asym
	playback.pcm{
		type hw
		card 0
	}
	capture.pcm{
		type hw
		card 1	
	}
}
ctl.!default{
	type hw
	card 0
}

*

____
## 2018_09_09
* I install raspbian 
____
