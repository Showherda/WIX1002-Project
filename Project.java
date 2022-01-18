import java.util.*;
import java.lang.*;

abstract class SameBehavior{
	public int attackPoint;
	public int criticalChancePercentage;
	public int accuracyPercentage=80;

	abstract public void increaseCriticalChancePercentage();
	abstract public void attack();
}

class Dragon extends SameBehavior{
	public int level=1;
	public int healthPoint=100;
	public int attackPoint=7;
	public int criticalChancePercentage=20;

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
}

class Tower extends SameBehavior{
	public int attackPoint=5;
	public int criticalChancePercentage=10;

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

	public void increaseAccuracyPercentage(){
		if (this.accuracyPercentage<100  && Project.gold>=100){
			Project.gold-=100;
			this.accuracyPercentage+=4;
		}
		else
			System.out.println("Insufficient gold or maximum accuracy percentage reached. Use another option.");
	}
}

class Wall{
	public int healthPoint=100;
	public int blockPercentage=10;

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
	public int emotional=10;
	public int nervous=10;
	public int lazy=10;
	public int berserk=10;
	public int diligent=10;
	public int fearless=10;

	public void decreaseEmotional(){
		if(emotional>=50 && Project.gold>=50){
			Project.gold-=50;
			emotional-=50;
		}
	}
	
	public void decreaseNervous(){
		if(nervous>=50 && Project.gold>=50){
			Project.gold-=50;
			nervous-=50;
		}
	}
	
	public void decreaseLazy(){
		if(lazy>=50 && Project.gold>=50){
			Project.gold-=50;
			lazy-=50;
		}
	}

	public void increaseEmotional(){
		emotional+=50;
		if (emotional>=100){
			Project.tower.attackPoint-=1;
			emotional-=100;
		}
	}

	public void increaseNervous(){
		nervous+=50;
		if (nervous>=100){
			Project.tower.accuracyPercentage-=5;
			nervous-=100;
		}
	}

	public void increaseLazy(){
		lazy+=50;
		if (lazy>=100){
			Project.wall.healthPoint-=100;
			lazy-=100;
		}
	}

	public void increaseBerserk(){
		if (Project.gold>=50){
			Project.gold-=50;
			berserk+=50;
		}
		if (berserk>=100){
			Project.tower.attackPoint+=1;
			berserk-=100;
		}
	}

	public void increaseDiligent(){
		if (Project.gold>=50){
			Project.gold-=50;
			diligent+=50;
		}
		if (diligent>=100){
			Project.wall.healthPoint+=75;
			diligent-=100;
		}
	}

	public void increaseFearless(){
		if (Project.gold>=50){
			Project.gold-=50;
			fearless+=50;
		}
		if (fearless>=100){
			Project.tower.criticalChancePercentage+=5;
			fearless-=100;
		}
	}
}

class Tax{
	public int taxCollected;

	public void collectTax(){
		Random rnd=new Random();
		taxCollected=new int[] {200,250,300,350,400}[rnd.nextInt(5)];
		Project.gold+=taxCollected;
	}
}

class Season{
	public String name;
}

class Spring extends Season{
	public String name="Spring";
	public Spring(){
		Random rnd=new Random();
		int event=rnd.nextInt(3);
		if (event==0){
			Project.tower.attackPoint+=1;
			Project.gameTime.event="Reinforcement arrived! Tower's AttackPoint increased by 1!";
		}
		else if (event==1){
			Project.gold+=100;
			Project.gameTime.event="Visitors arrived! You get 100 gold!";
		}
		else{
			Project.citizen.increaseBerserk();
			Project.citizen.increaseDiligent();
			Project.citizen.increaseFearless();
			Project.gameTime.event="Festival! Citizen's Berserk, Diligent and Fearless point increased by 50!";
		}
	}
}

class Summer extends Season{
	public String name="Summer";
	public Summer(){
		Random rnd=new Random();
		int event=rnd.nextInt(3);
		if (event==0){
			Project.wall.healthPoint-=50;
			Project.gameTime.event="Drought! Wall's HealthPoint decreased by 50!";
		}
		else if (event==1){
			Project.citizen.increaseBerserk();
			Project.citizen.increaseDiligent();
			Project.citizen.increaseFearless();
			Project.gameTime.event="Outing! Citizen's Berserk, Diligent and Fearless point increased by 50!";
		}
		else{
			Project.citizen.increaseEmotional();
			Project.citizen.increaseNervous();
			Project.citizen.increaseLazy();
			Project.gameTime.event="Heatstroke! Citizen's Emotional, Nervous and Lazy point increased by 50!";
		}
	}
}

class Autumn extends Season{
	public String name="Autumn";
	public Autumn(){
		Random rnd=new Random();
		int event=rnd.nextInt(3);
		if (event==0){
			Project.tower.accuracyPercentage-=20;
			Project.temporaryAccurcyPercentageFlag=true;
			Project.gameTime.event="Rainy! Tower's AccuracyPercentage dropped by 20% during this season!";
		}
		else if (event==1){
			Project.wall.healthPoint-=50;
			Project.gameTime.event="Flood! Wall's HealthPoint decreased by 50!";
		}
		else{
			Project.gold+=100;
			Project.gameTime.event="Harvest! You get 100 gold!";
		}
	}
}

class Winter extends Season{
	public String name="Winter";
	public Winter(){
		Random rnd=new Random();
		int event=rnd.nextInt(4);
		if (event==0){
			Project.wall.healthPoint-=50;
			Project.gameTime.event="Blizzard! Wall's HealthPoint decreased by 50!";
		}
		else if (event==1){
			Project.citizen.increaseEmotional();
			Project.citizen.increaseNervous();
			Project.citizen.increaseLazy();
			Project.gameTime.event="Avalanche! Citizen's Emotional, Nervous and Lazy point increased by 50!";
		}
		else if (event==2){
			Project.tower.accuracyPercentage-=20;
			Project.temporaryAccurcyPercentageFlag=true;
			Project.gameTime.event="Hunger! Tower's AccuracyPercentage decreased by 20 during the season!";
		}
		else{
			Project.gold+=100;
			Project.gameTime.event="Tour group arrived! You get 100 gold!";
		}
	}
}

class GameTime{
	public int year=1;
	public int seasonNum=0;
	public String event;
	public Season season;

	public void updateGameTime(){
		if (this.seasonNum==3){
			this.year+=1;
			this.seasonNum=0;
		}
		else
			this.seasonNum+=1;
		switch (this.seasonNum){
			case 0:
				season=new Spring();
				break;
			case 1:
				season=new Summer();
				break;
			case 2:
				season=new Autumn();
				break;
			case 3:
				season=new Winter();
				break;
		}
	}
}

class CommandLine{
	public CommandLine(){
		System.out.println("Event: "+Project.gameTime.event);
		System.out.println("Tax reveived from citizens this season: "+Project.tax.taxCollected);
		System.out.println("Year: "+Project.gameTime.year);
		System.out.println("Season: "+Project.gameTime.season.name);
		System.out.println("Gold: "+Project.gold);
		System.out.println("1: Tower");
		System.out.println("2: Wall");
		System.out.println("3: Citizens");
		System.out.println("4: Dragon");
		System.out.println("5: I am all ready!");
		System.out.println("Please enter your command: ");
	}
}

class TowerCommandLine{
	public TowerCommandLine(){
		System.out.println("Year: "+Project.gameTime.year);
		System.out.println("Season: "+Project.gameTime.season.name);
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
	}
}

class DragonCommandLine{
	public DragonCommandLine(){
		System.out.println("Year: "+Project.gameTime.year);
		System.out.println("Season: "+Project.gameTime.season.name);
		System.out.println("Gold: "+Project.gold);
		System.out.println("Dragon's Level: "+Project.dragon.level);
		System.out.println("Dragon's HealthPoint: "+Project.dragon.healthPoint);
		System.out.println("Dragon's AttackPoint: "+Project.dragon.attackPoint);
		System.out.println("Dragon's Critical Chance: "+Project.dragon.criticalChancePercentage);
		System.out.println("Dragon's Accuracy: "+Project.dragon.accuracyPercentage);
	}
}

class Project{
	public static Tower tower=new Tower();
	public static Wall wall=new Wall();
	public static Citizen citizen=new Citizen();
	public static Dragon dragon=new Dragon();
	public static boolean temporaryAccurcyPercentageFlag=false;
	public static Tax tax=new Tax();
	public static GameTime gameTime=new GameTime();
	public static int gold=200;
	
	public static void main(String[] args) {
		System.out.println("Welcome to Till The End - A tower Defense Game!");
		System.out.println("A dragon performs a sudden attack to your city!");
		System.out.println("Dragon's Level: "+dragon.level);
		System.out.println("Dragon's HealthPoint: "+dragon.healthPoint);
		System.out.println("Dragon's AttackPoint: "+dragon.attackPoint);
		System.out.println("Dragon's Critical Chance: "+dragon.criticalChancePercentage);
		System.out.println("Dragon's Accuracy: "+dragon.accuracyPercentage);

	}
}
