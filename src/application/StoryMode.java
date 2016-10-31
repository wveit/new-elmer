package application;



import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

public class StoryMode implements OpMode{

	Control control = null;
	Pane pane = new VBox();
	
	
	
    
	public StoryMode(){
	
		
		
		Label label = new Label("You wake in a dark room. You have no recollection of who you are or where you are. The only thing you do remember is "
				+ " basic motor function and speech. You feel around to understand your surroundings. After feeling around in the dark for what seemed "
				+ " like an eternity you find a tiny lever. You flick the lever with the middle of the index finger, the lights come on, you are able "
				+ " to see what is inside this dark room. The walls of this room are made of wood, you deduce that you are inside a cabin, perhaps in "
				+ " the woods. You spot two doors; you decide to turn the knob for the first one, but to no avail it is locked. Then the second knob is "
				+ " turned, it opens and inside is a restroom. You enter the restroom and find a mirror and you look upon it and are amazed at what you see. "
				+ " It is your visage, but something is wrong, you are covered from head to toe in black garbs with only a headband being a distinct Grey. "
				+ " To see what underneath the mask you pull it off, but to no avail, you do not see a face at all. You see an abyss and the longer to stare"
				+ " at it the more you feel it staring back at you. A thought goes through your mind, you figure that the mirror is cursed and thus determined"
				+ " that it would only show you what you feared the most, true darkness. With confused thoughts you leave the restroom to the main room, you "
				+ " then notice that the first door you tried to open is locked from the inside. Once you unlock is you open the door and a rush of bright "
				+ " light comes from outside the door. Once your eyes adjust to the brightness, you see that you are in fact in a forest; the leaves are "
				+ " turning from yellow to red, some still have a bit of green left on them. You run into the forest, you run, and run, but there seems to be "
				+ " no end. The scenery still looks the same, but then you come to a fork with two paths to choose from.");
		//Label label = new Label("Elmer The Confused Ninja");
		label.setFont(Font.font("Helvetica", 20));
		label.setMaxWidth(1180);
		//label.setMaxHeight(20);
        label.setAlignment(Pos.CENTER);
		label.setWrapText(true);
        label.setTextAlignment(TextAlignment.JUSTIFY);
		
		
       
		
//Start Volcano level
		Button miniGameButton = new Button("The first path is a large thicket with almost no visibility in front of you, though you can see smoke in the distant sky.");
		miniGameButton.setOnAction(e->{
			control.startPlatformer("volcano_level");
			
		if(control.getLastFinishCode()==0){
			pane.getChildren().clear();
			StoryP1O1Pass();
			}
		else {
			pane.getChildren().clear();
			StoryP1O1Fail();
		}
		});
		
		//Go To Cliff
		Button continueStoryButton = new Button("The second path is clear and leads to a high cliff.");
		continueStoryButton.setOnAction(e->{
			StoryP1O2Cliff();
		});
		
		
		//END STORY MODE
		Button endStoryModeButton = new Button("End Story Mode");
		endStoryModeButton.setOnAction(e->{
			control.notifyOfOpModeCompletion(this, 1);
		});

		pane.getChildren().addAll(label, miniGameButton, continueStoryButton, endStoryModeButton);
	}
//AFTER PLATFORMER
	public void StoryP1O1Pass(){
		Label label = new Label("After falling into a volcano, then managing to climb to the top the eagle's strength has been depleted. "
				+ "We start to fall and I, not wanting the eagle to become damaged I wrap my body to absorb any damage that may occur upon "
				+ "impacting the ground. The eagle is Oll Korrect, I on the other hand have a bit of bruising due to hitting many trees during "
				+ "our descent and hitting the ground forcefully. I pick up the eagle as it has not recovered from carrying me and so I take "
				+ "it with me. As we walk through more forest, both exhausted from our journeys the sky becomes overcast and starts to "
				+ "drizzle a bit. While still walking the same path the sky begins to cry over us its unrelenting tears. I decide to take "
				+ "the eagle under my shirt to keep it warm. We pass and enormous tree, I look up to see that there are many nests atop it and "
				+ "it seems that my feathered companion may in fact be one of its resident's. I take the eagle from under my shirt and it has "
				+ "just enough strength to fly to its nest. As I see it land I decide to continue on my journey of discovery in finding out "
				+ "who I am. It is still pouring out; I see a small cave I can take shelter in while the rain subsides. I also see that at a "
				+ "distance passing the cave is a field covered in white.");
		label.setFont(Font.font("Helvetica", 20));
		label.setMaxWidth(1180);
        label.setAlignment(Pos.CENTER);
		label.setWrapText(true);
        label.setTextAlignment(TextAlignment.JUSTIFY);
        
      //Go To Cave
      		Button continueStoryButton = new Button("I will go to the cave and rest until the rain subsides.");
      		continueStoryButton.setOnAction(e->{
      			StoryP2O1Cave();
      		});
      		
            //Go To Field
      		Button continueStoryButton2 = new Button("I decide that the best course of action is to forgo the cave since time is of the essence in this journey.");
      		continueStoryButton2.setOnAction(e->{
      			StoryP2O2Field();
      		});
        
        Button endStoryModeButton = new Button("End Story Mode");
		endStoryModeButton.setOnAction(e->{
			control.notifyOfOpModeCompletion(this, 1);
		});
		
		pane.getChildren().addAll(label, continueStoryButton, continueStoryButton2, endStoryModeButton);
        
	}
	
	public void StoryP1O1Fail(){
		Label label = new Label("I was not able to get out of the volcano in time. I was caught in the eruption, no, the explosion and I "
				+ "flew very high. As I am falling through the air I open my arms and legs as wide as I possibly can to reveal the flaps "
				+ "in my attire. The flaps in my wing suit must have been damaged as I am still descending extremely fast. I come crashing "
				+ "down a gathering of trees, I can consider myself luck that the trees broke my fall. I begin to walk in this forest and "
				+ "moments later I spot an eagle on the floor, it must have gotten caught in the explosion. The eagle is Oll Korrect, I on "
				+ "the other hand have a bit of bruising due to hitting many trees during my descent and hitting the ground forcefully. "
				+ "I pick up the eagle as it has not recovered from the volcanic explosion and so I take it with me. As we walk through more "
				+ "forest, both exhausted from our journeys the sky becomes overcast and starts to drizzle a bit. While still walking the same "
				+ "path the sky begins to cry over us its unrelenting tears. I decide to take the eagle under my shirt to keep it warm. "
				+ "We pass and enormous tree, I look up to see that there are many nests atop it and it seems that my feathered companion "
				+ "may in fact be one of its residents. I take the eagle from under my shirt and it has just enough strength to fly to its nest. "
				+ "As I see it land I decide to continue on my journey of discovery in finding out who I am. It is still pouring out; I see a small "
				+ "cave I can take shelter in while the rain subsides. I also see that at a distance passing the cave is a field covered in white.");
		label.setFont(Font.font("Helvetica", 20));
		label.setMaxWidth(1180);
        label.setAlignment(Pos.CENTER);
		label.setWrapText(true);
        label.setTextAlignment(TextAlignment.JUSTIFY);
        
      //Go To Cave
      		Button continueStoryButton = new Button("I will go to the cave and rest until the rain subsides.");
      		continueStoryButton.setOnAction(e->{
      			StoryP2O1Cave();
      		});
      		
            //Go To Field
      		Button continueStoryButton2 = new Button("I decide that the best course of action is to forgo the cave since time is of the essence in this journey.");
      		continueStoryButton2.setOnAction(e->{
      			StoryP2O2Field();
      		});
        
        Button endStoryModeButton = new Button("End Story Mode");
		endStoryModeButton.setOnAction(e->{
			control.notifyOfOpModeCompletion(this, 1);
		});
		
		pane.getChildren().addAll(label, continueStoryButton, continueStoryButton2, endStoryModeButton);
        
	}
//STORY PART 1 OPTION 2	
	public void StoryP1O2Cliff(){
		Label label = new Label("I decide to take the second path as climbing down the cliff will allow me to build more strength. I was dead wrong; "
				+ "this is the worst path I could have chosen to take. The cliff is very steep, the climb down very arduous. I hang on for life while "
				+ "I try to recover some energy, but it does not help after I hear a large explosion. The force of the explosion knocks me off the cliff. "
				+ "As I fall I muster all the strength I can and I open my arms and legs as wide as I possibly can to reveal the flaps in my attire. "
				+ "The flaps in my wing suit must have been damaged as I am still descending extremely fast. Then all of a sudden an eagle catches me "
				+ "and my descent is slowed significantly. We start to fall and I, not wanting the eagle to become damaged I wrap my body to absorb any "
				+ "damage that may occur upon impacting the ground. The eagle is Oll Korrect, I on the other hand have a bit of bruising due to hitting "
				+ "many trees during our descent and hitting the ground forcefully. I pick up the eagle as it has not recovered from carrying me and so "
				+ "I take it with me. As we walk through more forest, both exhausted from our journeys the sky becomes overcast and starts to drizzle a bit. "
				+ "While still walking the same path the sky begins to cry over us its unrelenting tears. I decide to take the eagle under my shirt to keep "
				+ "it warm. We pass and enormous tree, I look up to see that there are many nests atop it and it seems that my feathered companion may in "
				+ "fact be one of its residents. I take the eagle from under my shirt and it has just enough strength to fly to its nest. As I see it "
				+ "land I decide to continue on my journey of discovery in finding out who I am. It is still pouring out; I see a small cave I can take "
				+ "shelter in while the rain subsides. I also see that at a distance passing the cave is a field covered in white.");
		label.setFont(Font.font("Helvetica", 20));
		label.setMaxWidth(Double.MAX_VALUE/2);
        label.setAlignment(Pos.CENTER);
		label.setWrapText(true);
        label.setTextAlignment(TextAlignment.JUSTIFY);
        
        //Go To Cave
  		Button continueStoryButton = new Button("I will go to the cave and rest until the rain subsides.");
  		continueStoryButton.setOnAction(e->{
    			StoryP2O1Cave();
  		});
  		
        //Go To Field
  		Button continueStoryButton2 = new Button("I decide that the best course of action is to forgo the cave since time is of the essence in this journey.");
  		continueStoryButton2.setOnAction(e->{
     			StoryP2O2Field();
  		});
    
    Button endStoryModeButton = new Button("End Story Mode");
	endStoryModeButton.setOnAction(e->{
		control.notifyOfOpModeCompletion(this, 1);
	});
	
	pane.getChildren().addAll(label, continueStoryButton, continueStoryButton2, endStoryModeButton);
        
	}
	
//STORY PART 2 OPTION 1	
	public void StoryP2O1Cave(){
		Label label = new Label("I enter the dark cave. Besides being dark, it is also dry and cold. I start a fire and cuddle next to the fire for warmth. "
				+ "I lay my head to the floor and shut my eye to get a bit of rest before continuing. In my sleep I start to dream, I dream of a small "
				+ "child in a white field training to become a ninja. Could this young child be me? I ask, but alas it is not since I am able to his "
				+ "face clear as day. Who could this child be? I then ask, perhaps he is my son whom I was training to become a superior ninja to myself. "
				+ "I am then awoken to a maniacal laugh coming deep from within this cave. I grab a piece of firewood to use as a torch. I venture into "
				+ "the abyss of the cave I look at the wall and see murals of war, it seems like many ninjas fought a man in a Big Hat. Though the ninjas "
				+ "seem to have their heads decapitated, how can this be? I ask, ninja are the supreme warriors of the land. A ninjas strength is at "
				+ "the peak of humanity, their speed is unmatched, their sword is second to none. As I continue to walk the laughs get louder and louder. "
				+ "I see another light besides mine at the end of the path. As I reach the other light the laughs stop and I hear a rough voice exclaim I "
				+ "have been waiting for you Ninja, are you ready to be my next victim?. What I see next surprises me, it is a man in a Big Hat. "
				+ "Could this be the same man depicted on the cave wall, the one who decapitated many ninjas? I ask him who he was and if he was the "
				+ "same person depicted in the paintings and how it was possible to have defeated so many ninjas since they are the supreme warriors. "
				+ "His response was simple and concise, my name is Logan, and yes I am the one who has defeated so many ninjas. I ready my weapon and "
				+ "he continues still your weapon, ninja he says very calmly it is true that ninjas are the supreme warrior, but only PHYSICALLY. I ask "
				+ "what he meant, he responds I am a Wizard, the supreme warrior of the Mind. Your body may be the most superior, but your mind pales in "
				+ "comparison to mine. I ask you ninja do you wish to battle me in a game of wits?. I knew my response, I tell him");
		label.setFont(Font.font("Helvetica", 20));
		label.setMaxWidth(Double.MAX_VALUE/2);
        label.setAlignment(Pos.CENTER);
		label.setWrapText(true);
        label.setTextAlignment(TextAlignment.JUSTIFY);
        
        //Accept Wizard's Challenge
  		Button miniGameButton = new Button("Yes, I will take on any challenge to prove that the ninja is the supreme warrior in all aspects.");
  		miniGameButton.setOnAction(e->{
  			control.startPuzzle("story");
			
  			if(control.getLastFinishCode()==0){
  				StoryP2O1Pass();
  				}
  			else {
  				StoryP2O1Fail();
  			}
  		});
  		
        //Deny Wizard's Challenge 
  		Button continueStoryButton = new Button("No, I will not take on your challenge since your defeating of all other ninjas proves that we are not the supreme warrior in all aspects.");
  		continueStoryButton.setOnAction(e->{
     			StoryP2O1ChallengeDenied();
  		});
    
    Button endStoryModeButton = new Button("End Story Mode");
	endStoryModeButton.setOnAction(e->{
		control.notifyOfOpModeCompletion(this, 1);
	});
	
	pane.getChildren().addAll(label, miniGameButton, continueStoryButton, endStoryModeButton);
	}
	
	
	
//AFTER PUZZLE
		public void StoryP2O1Pass(){
			Label label = new Label("well it seems that not all ninjas lack the mental capacity to go head to head with the likes of me says the wizard "
					+ "after I had defeated him. He the continues his ramblings well young ninja, I the Wizard Logan, hereby claim you as the true supreme "
					+ "warrior of this land. He then points toward the back of the cave and says to me the exit is that way, continue on your journey and "
					+ "you might find answers someday. I leave the cave slightly happier than when I went into it. As I exit the cave I see the white field "
					+ "from before I see many white banana trees I am overcome with joy since bananas are my favorite fruit and from a distant memory it "
					+ "seems that the white bananas of the white banana tree are the most delicious. I gather as many as I could carry, but everything was "
					+ "quiet, too quiet. I was about to find out why it was too quiet, it was MONSTERS, I was surrounded by them and there was nowhere to run. "
					+ "I had to fight all these foes by myself, which I have done many a times. I reach to ready my weapon, but in all the excitement it "
					+ "seems that I have misplaced it and now I have to fight with the white bananas.");
			label.setFont(Font.font("Helvetica", 20));
			label.setMaxWidth(1180);
	        label.setAlignment(Pos.CENTER);
			label.setWrapText(true);
	        label.setTextAlignment(TextAlignment.JUSTIFY);
	        
	      //Start Top Down
	      		Button miniGameButton = new Button("I will defeat all these foes just like any other time I have faced enemies that outnumber me.");
	      		miniGameButton.setOnAction(e->{
	      			control.startDummyMiniGame("dummy_level");
	    			
	      			if(control.getLastFinishCode()==0){
	      				StoryEndA();
	      				}
	      			else {
	      				StoryEndB();
	      			}
	      		});

	        Button endStoryModeButton = new Button("End Story Mode");
			endStoryModeButton.setOnAction(e->{
				control.notifyOfOpModeCompletion(this, 1);
			});
			
			pane.getChildren().addAll(label, miniGameButton, endStoryModeButton);
	        
		}
		
		public void StoryP2O1Fail(){
			Label label = new Label("well it seems that all ninjas lack the mental capacity to go head to head with the likes of me says the wizard "
					+ "after he had defeated me. He the continues his ramblings well young ninja, I the Wizard Logan, give you this advice what you do "
					+ "with it is up to you; train your mind as you do your body and then perhaps one day you will surpass me. He then points toward the "
					+ "back of the cave and says to me the exit is that way, continue on your journey and you might find answers someday. I leave the "
					+ "cave slightly sadder than when I went into it. As I exit the cave I see the white field from before I see many white banana trees I "
					+ "am overcome with joy since bananas are my favorite fruit and from a distant memory it seems that the white bananas of the white "
					+ "banana tree are the most delicious. I gather as many as I could carry, but everything was quiet, too quiet. I was about to find out "
					+ "why it was too quiet, it was MONSTERS, I was surrounded by them and there was nowhere to run. I had to fight all these foes by myself, "
					+ "which I have done many a times. I reach to ready my weapon, but in all the excitement it seems that I have misplaced it and now I "
					+ "have to fight with the white bananas.");
			label.setFont(Font.font("Helvetica", 20));
			label.setMaxWidth(1180);
	        label.setAlignment(Pos.CENTER);
			label.setWrapText(true);
	        label.setTextAlignment(TextAlignment.JUSTIFY);
	        
	      //Start Top Down
	      		Button miniGameButton = new Button("I will defeat all these foes just like any other time I have faced enemies that outnumber me.");
	      		miniGameButton.setOnAction(e->{
	      			control.startDummyMiniGame("dummy_level");
	    			
	      			if(control.getLastFinishCode()==0){
	      				StoryEndA();
	      				}
	      			else {
	      				StoryEndB();
	      			}
	      		});

	        Button endStoryModeButton = new Button("End Story Mode");
			endStoryModeButton.setOnAction(e->{
				control.notifyOfOpModeCompletion(this, 1);
			});
			
			pane.getChildren().addAll(label, miniGameButton, endStoryModeButton);
	        
		}
		
//CALLENGE DENIED
		public void StoryP2O1ChallengeDenied(){
			Label label = new Label("well it seems that all ninjas lack the mental capacity to go head to head with the likes of me, but where your "
					+ "brethren were bested, you did not even try, you failed before you could see your true strength says the wizard after I had denied "
					+ "his challenge. He the continues his ramblings well young ninja, I the Wizard Logan, consider you the worst adversary I have ever "
					+ "had the displeasure of having in my presence. He then points toward the back of the cave and says to me the exit is that way, "
					+ "continue on your journey and you might find answers someday. I leave the cave sadder than when I went into it. As I exit the "
					+ "cave I see the white field from before I see many white banana trees I am overcome with joy since bananas are my favorite fruit "
					+ "and from a distant memory it seems that the white bananas of the white banana tree are the most delicious. I gather as many as I "
					+ "could carry, but everything was quiet, too quiet. I was about to find out why it was too quiet, it was MONSTERS, I was surrounded "
					+ "by them and there was nowhere to run. I had to fight all these foes by myself, which I have done many a times. I reach to ready "
					+ "my weapon, but in all the excitement it seems that I have misplaced it and now I have to fight with the white bananas.");
			label.setFont(Font.font("Helvetica", 20));
			label.setMaxWidth(Double.MAX_VALUE/2);
	        label.setAlignment(Pos.CENTER);
			label.setWrapText(true);
	        label.setTextAlignment(TextAlignment.JUSTIFY);
	        
	        //Start Top Down
	  		Button miniGameButton = new Button("I will defeat all these foes just like any other time I have faced enemies that outnumber me.");
	  		miniGameButton.setOnAction(e->{
	  			control.startDummyMiniGame("dummy_level");
				
	  			if(control.getLastFinishCode()==0){
	  				StoryEndA();
	  				}
	  			else {
	  				StoryEndB();
	  			}
	  		});

	    Button endStoryModeButton = new Button("End Story Mode");
		endStoryModeButton.setOnAction(e->{
			control.notifyOfOpModeCompletion(this, 1);
		});
		
		pane.getChildren().addAll(label, miniGameButton, endStoryModeButton);
		}
//STORY PART 2 OPTION 2	
		public void StoryP2O2Field(){
			Label label = new Label("As soon as I start to walk toward the white field I slip on the mud covered ground, I land face first and I slide "
					+ "down a hill. I get up and am covered in mud from head to toe, save for a small patch on my buttocks. As I start to walk the rain "
					+ "washes most of the mud that had accumulated when I had slipped and fell. After the rain completely finishes washing me of mud I "
					+ "look up at the sky and witness as the rays of the sun break through the overcast. Then in what seems to be a waking dream I see a "
					+ "small child in the white field training to become a ninja. Could this young child be me? I ask, but alas it is not since I am "
					+ "able to his face clear as day. Who could this child be? I then ask, perhaps he is my son whom I was training to become a superior "
					+ "ninja to myself. I then slip again in the mud, but I do not fall down. As I recover from the small slip the small child disappears, "
					+ "I concur that I was just having a waking dream. I was saddened for a moment because I was not able to resolve the meaning of the "
					+ "waking dream. Then as I reached the white field I see many white banana trees I am overcome with joy since bananas are my favorite "
					+ "fruit and from a distant memory it seems that the white bananas of the white banana tree are the most delicious. I gather as many "
					+ "as I could carry, but everything was quiet, too quiet. I was about to find out why it was too quiet, it was MONSTERS, I was "
					+ "surrounded by them and there was nowhere to run. I had to fight all these foes by myself, which I have done many a times. I reach "
					+ "to ready my weapon, but in all the excitement it seems that I have misplaced it and now I have to fight with the white bananas.");
			label.setFont(Font.font("Helvetica", 20));
			label.setMaxWidth(Double.MAX_VALUE/2);
	        label.setAlignment(Pos.CENTER);
			label.setWrapText(true);
	        label.setTextAlignment(TextAlignment.JUSTIFY);
	        
	        //Start Top Down
	  		Button miniGameButton = new Button("I will defeat all these foes just like any other time I have faced enemies that outnumber me.");
	  		miniGameButton.setOnAction(e->{
	  			control.startDummyMiniGame("dummy_level");
				
	  			if(control.getLastFinishCode()==0){
	  				StoryEndA();
	  				}
	  			else {
	  				StoryEndB();
	  			}
	  		});

	    Button endStoryModeButton = new Button("End Story Mode");
		endStoryModeButton.setOnAction(e->{
			control.notifyOfOpModeCompletion(this, 1);
		});
		
		pane.getChildren().addAll(label, miniGameButton, endStoryModeButton);
		}
	
	
//Story Ending
		public void StoryEndA(){
			Label label = new Label("The battle is over; the white field is now even more white than what it was before. It is covered in white bananas "
					+ "I have defeated all my foes except one. He stands motionless, staring right at me with one fruit in hand. I stare right back at "
					+ "him with one last fruit in hand too. We swing our hands forward and we each release our fruit. I see his fruit move in slow motion, "
					+ "so take it out of midair and throw it back at him. Both bananas strike him in the face he falls to the floor and I begin a victory cry.");
			label.setFont(Font.font("Helvetica", 20));
			label.setMaxWidth(Double.MAX_VALUE/2);
	        label.setAlignment(Pos.CENTER);
			label.setWrapText(true);
	        label.setTextAlignment(TextAlignment.JUSTIFY);

	    Button endStoryModeButton = new Button("End Story Mode");
		endStoryModeButton.setOnAction(e->{
			control.notifyOfOpModeCompletion(this, 1);
		});
		
		pane.getChildren().addAll(label, endStoryModeButton);
		}
		
		
		public void StoryEndB(){
			Label label = new Label("The battle is over; the white field is now even more white than what it was before. It is covered in white bananas "
					+ "I have defeated all my foes except one. He stands motionless, staring right at me with one fruit in hand. I stare right back at "
					+ "him with one last fruit in hand too. We swing our hands forward and we each release our fruit. I see his fruit move in slow motion, "
					+ "but I am paralyzed from the exhausting battle. My foe dodges my banana, his banana strikes me in the face I fall to the floor and he "
					+ "begins a victory cry.");
			label.setFont(Font.font("Helvetica", 20));
			label.setMaxWidth(Double.MAX_VALUE/2);
	        label.setAlignment(Pos.CENTER);
			label.setWrapText(true);
	        label.setTextAlignment(TextAlignment.JUSTIFY);

	    Button endStoryModeButton = new Button("End Story Mode");
		endStoryModeButton.setOnAction(e->{
			control.notifyOfOpModeCompletion(this, 1);
		});
		
		pane.getChildren().addAll(label, endStoryModeButton);
		}
	
	
	
	
	
	@Override
	public void setControl(Control control) {
		this.control = control;
	}

	@Override
	public Pane getPane() {
		return pane;
	}

	@Override
	public void start() {
		
	}

	@Override
	public void pause() {
		
	}

	@Override
	public void resume() {
		
	}

	@Override
	public void end() {
		
	}

	@Override
	public void load(String filename) {
		
	}

}