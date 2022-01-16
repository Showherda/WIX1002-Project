import java.util.*;
import java.lang.*;

public abstract class SameBehavior{
	public int attackPoint;
	public int criticalChancePercentage;
	public int accuracyPercentage=80;

	abstract public void increaseCriticalChancePercentage();
	abstract public void increaseAccuracyPercentage();
}

public class Dragon extends SameBehavior{
	public int level=1;
	public int healthPoint=100;
	attackPoint=7;
	criticalChancePercentage=20;

	public void increaseCriticalChancePercentage(){
		if (this.criticalChancePercentage<50)
			this.criticalChancePercentage+=2;
	}

	public void levelUp(){
		if (this.healthPoint<100)
			this.healthPoint=100;
		this.healthPoint+=15;
		this.attackPoint+=1;
		this.increaseCriticalChancePercentage();
	}
}

public class Tower extends SameBehavior{
	attackPoint=5;
	criticalChancePercentage=10;

	public void increaseAttackPoint(){
		if (tax>=100){
			tax-=100;
			this.attackPoint+=1;
		}
		else
			System.out.println("Insufficient gold. Use another option.");
	}

	public void increaseCriticalChangePercentage(){
		if (this.criticalChancePercentage<50  && tax>=100){
			tax-=100;
			this.criticalChancePercentage+=5;
		}
		else
			System.out.println("Insufficient gold or maximum critical chance percentage reached. Use another option.");
	}

	public void increaseAccuracyPercentage(){
		if (this.accuracyPercentage<100  && tax>=100){
			tax-=100;
			this.accuracyPercentage+=4;
		}
		else
			System.out.println("Insufficient gold or maximum accuracy percentage reached. Use another option.");
	}
}

public class Wall{
	public int healthPoint=100;
	public int blockPercentage=10;

	void public increaseHealthPoint(){
		if (tax>=100){
			tax-=100;
			this.healthPoint+=75;
		}
	}

	void public increaseBlockPercentage(){
		if (this.blockPercentage<50 && tax>=100){
			tax-=100;
			this.blockPercentage+=5;
		}
		else{
			System.out.println("Insufficient gold or maximum block percentage reached. Use another option.");
		}
	}
}

public class Citizen{
	public int emotional=10;
	public int nervous=10;
	public int lazy=10;
	public int berserk=10;
	public int diligent=10;
	public int fearless=10;

	public void decreaseEmotional(){
		if(emotional>=50 && tax>=50){
			tax-=50;
			emotional-=50;
		}
	}
	
	public void decreaseNervous(){
		if(nervous>=50 && tax>=50){
			tax-=50;
			nervous-=50;
		}
	}
	
	public void decreaseLazy(){
		if(lazy>=50 && tax>=50){
			tax-=50;
			lazy-=50;
		}
	}

	public void increaseEmotional(){
		emotional+=50;
		if (emotional>=100){
			tower.attackPoint-=1;
			emotional-=100;
		}
	}

	public void increaseNervous(){
		nervous+=50;
		if (nervous>=100){
			tower.accuracyPercentage-=5;
			nervous-=100;
		}
	}

	public void increaseLazy(){
		lazy+=50;
		if (lazy>=100){
			wall.healthPoint-=100;
			lazy-=100;
		}
	}

	public void increaseBerserk(){
		if (tax>=50){
			tax-=50;
			berserk+=50;
		}
		if (berserk>=100){
			tower.attackPoint+=1;
			berserk-=100;
		}
	}

	public void increaseDiligent(){
		if (tax>=50){
			tax-=50;
			diligent+=50;
		}
		if (diligent>=100){
			wall.healthPoint+=75;
			diligent-=100;
		}
	}

	public void increaseFearless(){
		if (tax>=50){
			tax-=50;
			fearless+=50;
		}
		if (fearless>=100){
			tower.criticalChancePercentage+=5;
			fearless-=100;
		}
	}
}

public class Tax{
	public static tax=200;
	
	public void collectTax(){
		Random rnd=new Random();
		tax+=new int[] {200,250,300,350,400}[rnd.nextInt(5)];
	}
}

public class Spring{
	public Spring(){
		Random rnd=new Random();
		int event=rnd.nextInt(3);
		if (event==0)
			tower.attackPoint+=1;
		else if (event==1)
			tax+=100;
		else{
			citizen.increaseBerserk();
			citizen.increaseDiligent();
			citizen.increaseFearless();
		}
	}
}

public class Summer{
	public Summer(){
		Random rnd=new Random();
		int event=rnd.nextInt(3);
		if (event==0)
			wall.healthPoint-=50;
		else if (event==1){
			citizen.increaseBerserk();
			citizen.increaseDiligent();
			citizen.increaseFearless();
		}
		else{
			citizen.increaseEmotional();
			citizen.increaseNervous();
			citizen.increaseLazy();
		}
	}
}

public class Autumn{
	public Autumn(){
		Random rnd=new Random();
		int event=rnd.nextInt(3);
		if (event==0){
			tower.accuracyPercentage-=20;
			temporaryAccurcyPercentageFlag=true;
		}
		else if (event==1)
			wall.healthPoint-=50;
		else
			tax+=100;
	}
}

public class Winter{
	public Winter(){
		Random rnd=new Random();
		int event=rnd.nextInt(4);
		if (event==0)
			wall.healthPoint-=50;
		else if (event==1){
			citizen.increaseEmotional();
			citizen.increaseNervous();
			citizen.increaseLazy();
		}
		else if (event==2){
			tower.accuracyPercentage-=20;
			temporaryAccurcyPercentageFlag=true;
		}
		else
			tax+=100;
	}
}

public class Project{
	public static Tower tower=new Tower();
	public static Wall wall=new Wall();
	public static Citizen citizen=new Citizen();
	public static Dragon dragon=new Dragon();
	public static boolean temporaryAccurcyPercentageFlag=false;

	public void dragonAttack(){
		Random rnd=new Random();
		boolean dragonAttackSuccess=(rnd.nextInt(101)<=dragon.accuracyPercentage),
		dragonCriticalAttack=(rnd.nextInt(101)<=dragon.criticalChancePercentage),
		wallBlockSuccess=(rnd.nextInt(101)<=wall.blockPercentage);

		if (wallBlockSuccess)
			System.out.println("Wall successfully blocked dragon's attack!\nCurrent Wall's HealthPoint: "+wall.healthPoint);
		else{
			if (dragonAttackSuccess && dragonCriticalAttack){
				int change=(int)dragon.attackPoint*1.0*(1.0+dragon.attackPoint*1.0/100.0);
				wall.healthPoint-=change;
				System.out.println("Dragon attacked wall with critical attack!\nWall's HealthPoint minus "+change+"\nCurrent Wall's HealthPoint: "+wall.healthPoint);
			}
			if (dragonAttackSuccess){
				int change=(int)dragon.attackPoint;
				wall.healthPoint-=change;
				System.out.println("Dragon attacked our wall\nWall's HealthPoint minus "+change+"\nCurrent Wall's HealthPoint: "+wall.healthPoint);
			}
		}
	}

	public void towerAttack(){
		Random rnd=new Random();
		boolean towerAttackSuccess=(rnd.nextInt(101)<=tower.accuracyPercentage),
		towerCriticalAttack=(rnd.nextInt(101)>=tower.criticalChancePercentage);

		if (towerAttackSuccess && towerCriticalAttack){
			int change=(int)tower.attackPoint*1.0*(1.0+dragon.attackPoint*1.0/100.0);
			dragon.healthPoint-=change;
			System.out.println("Tower attacked dragon with critical attack!\nDragon's HealthPoint minus "+change+"\nCurrent Dragon's HealthPoint: "+dragon.healthPoint);
		}
		if (towerAttackSuccess){
			int change=(int)tower.attackPoint;
			dragon.healthPoint-=change;
			System.out.println("Tower attacked dragon!\nDragon's HealthPoint minus "+change+"\nCurrent Dragon's HealthPoint: "+dragon.healthPoint);
		}
	}
}
