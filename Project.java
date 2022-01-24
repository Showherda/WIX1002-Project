import java.util.*;
import java.lang.*;

abstract class SameBehavior{
	protected int attackPoint;
	protected int criticalChancePercentage;
	protected int accuracyPercentage=80;

	abstract public void increaseCriticalChancePercentage();
	abstract public void attack();
}

class Dragon extends SameBehavior{
	protected int level=1;
	protected int healthPoint=100;
	protected int attackPoint=7;
	protected int criticalChancePercentage=20;

	public void increaseCriticalChancePercentage(){
		if (this.criticalChancePercentage<50)
			this.criticalChancePercentage+=2;
	}

	public void attack(){
		Random rnd=new Random();
		boolean dragonAttackSuccess=(rnd.nextInt(101)<=Project.dragon.accuracyPercentage),
		dragonCriticalAttack=(rnd.nextInt(101)<=Project.dragon.criticalChancePercentage),
		wallBlockSuccess=(rnd.nextInt(101)<=Project.wall.blockPercentage);

		if (wallBlockSuccess)
			System.out.println("Wall successfully blocked dragon's attack!\nCurrent Wall's HealthPoint: "+Project.wall.healthPoint);
		else{
			if (dragonAttackSuccess && dragonCriticalAttack){
				int change=(int)((Project.dragon.attackPoint*1.0)*(1.0+Project.dragon.attackPoint*1.0/100.0));
				Project.wall.healthPoint-=change;
				System.out.println("Dragon attacked wall with critical attack!\nWall's HealthPoint minus "+change+"\nCurrent Wall's HealthPoint: "+Project.wall.healthPoint);
			}
			if (dragonAttackSuccess){
				int change=(int)Project.dragon.attackPoint;
				Project.wall.healthPoint-=change;
				System.out.println("Dragon attacked our wall\nWall's HealthPoint minus "+change+"\nCurrent Wall's HealthPoint: "+Project.wall.healthPoint);
			}
		}
	}

	public void levelUp(){
		if (this.healthPoint<100)
			this.healthPoint=100;
		this.level+=1;
		this.healthPoint+=15;
		this.attackPoint+=1;
		this.increaseCriticalChancePercentage();
	}

	public void displayInfo(){
		System.out.println("Dragon's Level: "+this.level);
		System.out.println("Dragon's HealthPoint: "+this.healthPoint);
		System.out.println("Dragon's AttackPoint: "+this.attackPoint);
		System.out.println("Dragon's Critical Chance: "+this.criticalChancePercentage);
		System.out.println("Dragon's Accuracy: "+this.accuracyPercentage);
	}
}

class Tower extends SameBehavior{
	protected int attackPoint=5;
	protected int criticalChancePercentage=10;

	public void increaseAttackPoint(){
		if (Project.gold>=100){
			Project.gold-=100;
			this.attackPoint+=1;
		}
		else
			System.out.println("Insufficient gold. Use another option.");
	}

	public void increaseCriticalChancePercentage(){
		if (this.criticalChancePercentage<50  && Project.gold>=100){
			Project.gold-=100;
			this.criticalChancePercentage+=5;
		}
		else
			System.out.println("Insufficient gold or maximum critical chance percentage reached. Use another option.");
	}

	public void increaseAccuracyPercentage(){
		if (this.accuracyPercentage<100  && Project.gold>=100){
			Project.gold-=100;
			this.accuracyPercentage+=4;
		}
		else
			System.out.println("Insufficient gold or maximum accuracy percentage reached. Use another option.");
	}

	public void attack(){
		Random rnd=new Random();
		boolean towerAttackSuccess=(rnd.nextInt(101)<=Project.tower.accuracyPercentage),
		towerCriticalAttack=(rnd.nextInt(101)>=Project.tower.criticalChancePercentage);

		if (towerAttackSuccess && towerCriticalAttack){
			int change=(int)((Project.tower.attackPoint*1.0)*(1.0+Project.tower.attackPoint*1.0/100.0));
			Project.dragon.healthPoint-=change;
			System.out.println("Tower attacked dragon with critical attack!\nDragon's HealthPoint minus "+change+"\nCurrent Dragon's HealthPoint: "+Project.dragon.healthPoint);
		}
		if (towerAttackSuccess){
			int change=(int)Project.tower.attackPoint;
			Project.dragon.healthPoint-=change;
			System.out.println("Tower attacked dragon!\nDragon's HealthPoint minus "+change+"\nCurrent Dragon's HealthPoint: "+Project.dragon.healthPoint);
		}
	}
}

class Wall{
	protected int healthPoint=100;
	protected int blockPercentage=10;

	public void increaseHealthPoint(){
		if (Project.gold>=100){
			Project.gold-=100;
			this.healthPoint+=75;
		}
	}

	public void increaseBlockPercentage(){
		if (this.blockPercentage<50 && Project.gold>=100){
			Project.gold-=100;
			this.blockPercentage+=5;
		}
		else{
			System.out.println("Insufficient gold or maximum block percentage reached. Use another option.");
		}
	}
}

class Citizen{
	protected int emotional=10;
	protected int nervous=10;
	protected int lazy=10;
	protected int berserk=10;
	protected int diligent=10;
	protected int fearless=10;

	public void decreaseEmotional(){
		if(this.emotional>=50 && Project.gold>=50){
			Project.gold-=50;
			this.emotional-=50;
		}
	}
	
	public void decreaseNervous(){
		if(this.nervous>=50 && Project.gold>=50){
			Project.gold-=50;
			this.nervous-=50;
		}
	}
	
	public void decreaseLazy(){
		if(this.lazy>=50 && Project.gold>=50){
			Project.gold-=50;
			this.lazy-=50;
		}
	}

	public void increaseEmotional(){
		this.emotional+=50;
		if (this.emotional>=100){
			Project.tower.attackPoint-=1;
			this.emotional-=100;
		}
	}

	public void increaseNervous(){
		this.nervous+=50;
		if (this.nervous>=100){
			Project.tower.accuracyPercentage-=5;
			this.nervous-=100;
		}
	}

	public void increaseLazy(){
		this.lazy+=50;
		if (this.lazy>=100){
			Project.wall.healthPoint-=100;
			this.lazy-=100;
		}
	}

	public void increaseBerserk(){
		if (Project.gold>=50){
			Project.gold-=50;
			this.berserk+=50;
		}
		if (this.berserk>=100){
			Project.tower.attackPoint+=1;
			this.berserk-=100;
		}
	}

	public void increaseDiligent(){
		if (Project.gold>=50){
			Project.gold-=50;
			this.diligent+=50;
		}
		if (this.diligent>=100){
			Project.wall.healthPoint+=75;
			this.diligent-=100;
		}
	}

	public void increaseFearless(){
		if (Project.gold>=50){
			Project.gold-=50;
			this.fearless+=50;
		}
		if (this.fearless>=100){
			Project.tower.criticalChancePercentage+=5;
			this.fearless-=100;
		}
	}
}

class Tax{
	protected int taxCollected;

	public void collectTax(){
		Random rnd=new Random();
		if (Project.hardMode)
			this.taxCollected=new int[] {150,200,250,300,350}[rnd.nextInt(5)];
		else
			this.taxCollected=new int[] {200,250,300,350,400}[rnd.nextInt(5)];
		Project.gold+=taxCollected;
	}
}

class Season{
	protected String name;
	
	public Season(String name){
		this.name=name;
	}
}

class Spring extends Season{
	public Spring(){
		super("Spring");
		Random rnd=new Random();
		int event=rnd.nextInt(3);
		if (event==0){
			Project.tower.attackPoint+=1;
			Project.gameTime.event="Event: Reinforcement arrived! Tower's AttackPoint increased by 1!";
		}
		else if (event==1){
			Project.gold+=100;
			Project.gameTime.event="Event: Visitors arrived! You get 100 gold!";
		}
		else{
			Project.citizen.berserk+=50;
			Project.citizen.diligent+=50;
			Project.citizen.fearless+=50;
			Project.gameTime.event="Event: Festival! Citizen's Berserk, Diligent and Fearless point increased by 50!";
		}
	}
}

class Summer extends Season{
	public Summer(){
		super("Summer");
		Random rnd=new Random();
		int event=rnd.nextInt(3);
		if (event==0){
			Project.wall.healthPoint-=50;
			Project.gameTime.event="Event: Drought! Wall's HealthPoint decreased by 50!";
		}
		else if (event==1){
			Project.citizen.berserk+=50;
			Project.citizen.diligent+=50;
			Project.citizen.fearless+=50;
			Project.gameTime.event="Event: Outing! Citizen's Berserk, Diligent and Fearless point increased by 50!";
		}
		else{
			Project.citizen.emotional+=50;
			Project.citizen.nervous+=50;
			Project.citizen.lazy+=50;
			Project.gameTime.event="Event: Heatstroke! Citizen's Emotional, Nervous and Lazy point increased by 50!";
		}
	}
}

class Autumn extends Season{
	public Autumn(){
		super("Autumn");
		Random rnd=new Random();
		int event=rnd.nextInt(3);
		if (event==0){
			Project.tower.accuracyPercentage-=20;
			Project.temporaryAccurcyPercentageFlag=true;
			Project.gameTime.event="Event: Rainy! Tower's AccuracyPercentage dropped by 20% during this season!";
		}
		else if (event==1){
			Project.wall.healthPoint-=50;
			Project.gameTime.event="Event: Flood! Wall's HealthPoint decreased by 50!";
		}
		else{
			Project.gold+=100;
			Project.gameTime.event="Event: Harvest! You get 100 gold!";
		}
	}
}

class Winter extends Season{
	public Winter(){
		super("Winter");
		Random rnd=new Random();
		int event=rnd.nextInt(6);
		if (event==0){
			Project.wall.healthPoint-=50;
			Project.citizen.emotional+=50;
			Project.citizen.nervous=50;
			Project.citizen.lazy+=50;
			Project.gameTime.event="Event 1: Blizzard! Wall's HealthPoint decreased by 50!\nEvent 2: Avalanche! Citizen's Emotional, Nervous and Lazy point increased by 50!";
		}
		else if (event==1){
			Project.wall.healthPoint-=50;
			Project.tower.accuracyPercentage-=20;
			Project.temporaryAccurcyPercentageFlag=true;
			Project.gameTime.event="Event 1: Blizzard! Wall's HealthPoint decreased by 50!\nEvent 2: Hunger! Tower's AccuracyPercentage decreased by 20 during the season!";
		}
		else if (event==2){
			Project.wall.healthPoint-=50;
			Project.gold+=100;
			Project.gameTime.event="Event 1: Blizzard! Wall's HealthPoint decreased by 50!\nEvent 2: Tour group arrived! You get 100 gold!";
		}
		else if (event==3){
			Project.citizen.emotional+=50;
			Project.citizen.nervous=50;
			Project.citizen.lazy+=50;
			Project.tower.accuracyPercentage-=20;
			Project.temporaryAccurcyPercentageFlag=true;
			Project.gameTime.event="Event 1: Avalanche! Citizen's Emotional, Nervous and Lazy point increased by 50!\nEvent 2: Hunger! Tower's AccuracyPercentage decreased by 20 during the season!";
		}
		else if (event==4){
			Project.citizen.emotional+=50;
			Project.citizen.nervous=50;
			Project.citizen.lazy+=50;
			Project.gold+=100;
			Project.gameTime.event="Event 1: Avalanche! Citizen's Emotional, Nervous and Lazy point increased by 50!\nTour group arrived! You get 100 gold!";
		}
		else{
			Project.tower.accuracyPercentage-=20;
			Project.temporaryAccurcyPercentageFlag=true;
			Project.gold+=100;
			Project.gameTime.event="Event 1: Hunger! Tower's AccuracyPercentage decreased by 20 during the season!\nEvent 2: Tour group arrived! You get 100 gold!";
		}
	}
}

class GameTime{
	protected int year=1;
	protected int seasonNum=-1;
	protected String event;
	protected Season season;

	public void updateGameTime(){
		if (this.seasonNum==3){
			this.year+=1;
			this.seasonNum=0;
		}
		else
			this.seasonNum+=1;
		switch (this.seasonNum){
			case 0:
				this.season=new Winter();//Spring();
				break;
			case 1:
				this.season=new Summer();
				break;
			case 2:
				this.season=new Autumn();
				break;
			case 3:
				this.season=new Winter();
				break;
		}

		if (Project.temporaryAccurcyPercentageFlag)
			Project.tower.accuracyPercentage+=20;
	}

	public void displayInfo(){
		System.out.println("Year: "+Project.gameTime.year);
		System.out.println("Season: "+Project.gameTime.season.name);
	}
}

class CommandLine{
	;
}

class MainCommandLine extends CommandLine{
	public MainCommandLine(){
		System.out.println(Project.gameTime.event);
		System.out.println("Tax reveived from citizens this season: "+Project.tax.taxCollected);
		Project.gameTime.displayInfo();
		System.out.println("Gold: "+Project.gold);
		System.out.println("1: Tower");
		System.out.println("2: Wall");
		System.out.println("3: Citizens");
		System.out.println("4: Dragon");
		System.out.println("5: Go for hard mode");
		System.out.println("6: I am all ready!");
		System.out.println("Please enter your command: ");

		Scanner scanner=new Scanner(System.in);
		int command;
		String tmp;
		while (true){
			if (scanner.hasNextInt()){
				tmp=scanner.next();
				command=Integer.parseInt(tmp);
				if (command>=1 && command <=6)
					break;
				else
					System.out.println("Enter an integer between 1 and 6");
			}
			else{
				tmp=scanner.next();
				System.out.println("Enter an integer between 1 and 6");
			}
		}
		switch (command){
			case 1:
				Project.commandLine=new TowerCommandLine();
				break;
			case 2:
				Project.commandLine=new WallCommandLine();
				break;
			case 3:
				Project.commandLine=new CitizenCommandLine();
				break;
			case 4:
				Project.commandLine=new DragonCommandLine();
				break;
			case 5:
				Project.hardMode=true;
				Project.dragon.attackPoint+=2;
				Project.dragon.accuracyPercentage+=5;
				Project.dragon.criticalChancePercentage+=5;
				Project.commandLine=new MainCommandLine();
				break;
			case 6:
				break;
		}
	}
}

class TowerCommandLine extends CommandLine{
	public TowerCommandLine(){
		Project.gameTime.displayInfo();
		System.out.println("Gold: "+Project.gold);
		System.out.println("Tower's AttackPoint: "+Project.tower.attackPoint);
		System.out.println("Tower's Critical Chance: "+Project.tower.criticalChancePercentage);
		System.out.println("Tower's Accuracy: "+Project.tower.accuracyPercentage);
		System.out.println();
		System.out.println("1: Upgrade Attack (100 Gold -> 1 AttackPoint)");
		System.out.println("2: Upgrade Critical Chance (100 Gold -> 5 Critical Chance %)");
		System.out.println("3: Upgrade Accuracy (100 Gold -> 4% Accuracy)");
		System.out.println("4: Back to menu");
		System.out.println("Please enter your command: ");

		Scanner scanner=new Scanner(System.in);
		int command;
		String tmp;
		while (true){
			if (scanner.hasNextInt()){
				tmp=scanner.next();
				command=Integer.parseInt(tmp);
				if (command>=1 && command <=4)
					break;
				else
					System.out.println("Enter an integer between 1 and 4");
			}
			else{
				tmp=scanner.next();
				System.out.println("Enter an integer between 1 and 4");
			}
		}
		switch (command){
			case 1:
				Project.tower.increaseAttackPoint();
				Project.commandLine=new TowerCommandLine();
				break;
			case 2:
				Project.tower.increaseCriticalChancePercentage();
				Project.commandLine=new TowerCommandLine();
				break;
			case 3:
				Project.tower.increaseAccuracyPercentage();
				Project.commandLine=new TowerCommandLine();
				break;
			case 4:
				Project.commandLine=new MainCommandLine();
				break;
		}
	}
}

class DragonCommandLine extends CommandLine{
	public DragonCommandLine(){
		Project.gameTime.displayInfo();
		System.out.println("Gold: "+Project.gold);
		Project.dragon.displayInfo();
		System.out.println("1: Back to menu");
		System.out.println("Please enter your command: ");

		Scanner scanner=new Scanner(System.in);
		int command;
		String tmp;
		while (true){
			if (scanner.hasNextInt()){
				tmp=scanner.next();
				command=Integer.parseInt(tmp);
				if (command==1){
					Project.commandLine=new MainCommandLine();
					break;
				}
				else
					System.out.println("Enter 1");
			}
			else{
				tmp=scanner.next();
				System.out.println("Enter 1");
			}
		}
	}
}

class WallCommandLine extends CommandLine{
	public WallCommandLine(){
		Project.gameTime.displayInfo();
		System.out.println("Gold: "+Project.gold);
		System.out.println("Wall's HealthPoint: "+Project.wall.healthPoint);
		System.out.println("Wall's Block: "+Project.wall.blockPercentage);
		System.out.println();
		System.out.println("1: Upgrade Health (100 Gold -> 75 HealthPoint)");
		System.out.println("2: Upgrade Block Chance (100 Gold -> 5 Block Chance %)");
		System.out.println("3: Back to menu");
		System.out.println("Please enter your command: ");

		Scanner scanner=new Scanner(System.in);
		int command;
		String tmp;
		while (true){
			if (scanner.hasNextInt()){
				tmp=scanner.next();
				command=Integer.parseInt(tmp);
				if (command>=1 && command <=3)
					break;
				else
					System.out.println("Enter an integer between 1 and 5");
			}
			else{
				tmp=scanner.next();
				System.out.println("Enter an integer between 1 and 3");
			}
		}
		switch (command){
			case 1:
				Project.wall.increaseHealthPoint();
				Project.commandLine=new WallCommandLine();
				break;
			case 2:
				Project.wall.increaseBlockPercentage();
				Project.commandLine=new WallCommandLine();
				break;
			case 3:
				Project.commandLine=new MainCommandLine();
				break;
		}
	}
}

class CitizenCommandLine extends CommandLine{
	public CitizenCommandLine(){
		Project.gameTime.displayInfo();
		System.out.println("Gold: "+Project.gold);
		System.out.println("Citizen's Emotional (Decrease Tower's AttackPoint by 1): "+Project.citizen.emotional);
		System.out.println("Citizen's Nervous (Decrease Tower Accuracy Percentage by 5%): "+Project.citizen.nervous);
		System.out.println("Citizen's Lazy (Decrease Wall's HealthPoint by 100): "+Project.citizen.lazy);
		System.out.println("Citizen's Berserk (Increase Tower's AttackPoint by 1): "+Project.citizen.berserk);
		System.out.println("Citizen's Diligent (Increase Wall's HealthPoint by 75): "+Project.citizen.diligent);
		System.out.println("Citizen's Fearless (Increase Tower's Critical Attack Chance Percentage by 5%): "+Project.citizen.fearless);
		System.out.println("1: Decrease Emotional (50 Gold -> 50 Emotional Point)");
		System.out.println("2: Decrease Nervous (50 Gold -> 50 Nervous Point)");
		System.out.println("3: Decrease Lazy (50 Gold -> 50 Lazy Point)");
		System.out.println("4: Increase Berserk (50 Gold -> 50 Berserk Point)");
		System.out.println("5: Increase Diligent (50 Gold -> 50 Diligent Point");
		System.out.println("6: Increase Fearless (50 Gold -> 50 Fearless Point)");
		System.out.println("7: Back to menu");
		System.out.println("Please enter your command: ");

		Scanner scanner=new Scanner(System.in);
		int command;
		String tmp;
		while (true){
			if (scanner.hasNextInt()){
				tmp=scanner.next();
				command=Integer.parseInt(tmp);
				if (command>=1 && command <=7)
					break;
				else
					System.out.println("Enter an integer between 1 and 5");
			}
			else{
				tmp=scanner.next();
				System.out.println("Enter an integer between 1 and 7");
			}
		}
		switch (command){
			case 1:
				Project.citizen.decreaseEmotional();
				Project.commandLine=new CitizenCommandLine();
				break;
			case 2:
				Project.citizen.decreaseNervous();
				Project.commandLine=new CitizenCommandLine();
				break;
			case 3:
				Project.citizen.decreaseLazy();
				Project.commandLine=new CitizenCommandLine();
				break;
			case 4:
				Project.citizen.increaseBerserk();
				Project.commandLine=new CitizenCommandLine();
				break;
			case 5:
				Project.citizen.increaseDiligent();
				Project.commandLine=new CitizenCommandLine();
				break;
			case 6:
				Project.citizen.increaseFearless();
				Project.commandLine=new CitizenCommandLine();
				break;
			case 7:
				Project.commandLine=new MainCommandLine();
				break;
		}
	}
}

public class Project{
	protected static Tower tower=new Tower();
	protected static Wall wall=new Wall();
	protected static Citizen citizen=new Citizen();
	protected static Dragon dragon=new Dragon();
	protected static boolean temporaryAccurcyPercentageFlag=false;
	protected static boolean hardMode=false;
	protected static Tax tax=new Tax();
	protected static GameTime gameTime=new GameTime();
	protected static int gold=200;
	protected static CommandLine commandLine;

	public static void game(){
		tax.collectTax();
		gameTime.updateGameTime();
		dragon.displayInfo();
		commandLine=new MainCommandLine();
		int cnt=1;

		while (true){
			if (cnt%20==0){
				tax.collectTax();
				gameTime.updateGameTime();
				commandLine=new MainCommandLine();
			}
			checkStatus();
			if (cnt%2==0)
				dragon.attack();
			else
				tower.attack();
			cnt+=1;
		}
	}

	public static void checkStatus(){
		if (dragon.healthPoint<=0){
			System.out.println("Congrats, you have won! The dragon's HealthPoint is "+dragon.healthPoint);
			System.out.println("Press any key to exit the game!");

			Scanner scanner=new Scanner(System.in);
			String inp=scanner.next();
			System.exit(0);
		}

		if (wall.healthPoint<=0){
			System.out.println("Oh no, you lost! Your wall's HealthPoint is "+wall.healthPoint);
			System.out.println("Press any key to exit the game!");

			Scanner scanner=new Scanner(System.in);
			String inp=scanner.next();
			System.exit(0);
		}
	}

	public static void main(String[] args){
		System.out.println("Welcome to Till The End - A tower Defense Game!");
		System.out.println("A dragon performs a sudden attack to your city!");
		game();
	}
}
