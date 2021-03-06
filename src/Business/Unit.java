package Business;

public abstract class Unit extends Tile implements Visitor {
    protected String name;
    protected Health health;
    protected int attack_pts;
    protected int defense_pts;
    protected MessageCallback messageCallback;

    public Unit(char tile, String name, int health_pool, int attack_pts, int defense_pts)
    {
        super(tile);
        this.name = name;
        this.health = new Health(health_pool);
        this.attack_pts = attack_pts;
        this.defense_pts = defense_pts;
    }

    public void setMessageCallback(MessageCallback messageCallback){
        this.messageCallback = messageCallback;
    }

    public String getName(){
        return this.name;
    }

    protected int attack(){
        int attackRoll = (int)(Math.random() * (attack_pts + 1));
        messageCallback.send(getName() + " rolled " + attackRoll + " attack points.");
        return attackRoll;
    }

    public int defend(){
        int defenseRoll = (int)(Math.random() * (defense_pts + 1));
        messageCallback.send(getName() + " rolled " + defenseRoll + " defense points.");
        return defenseRoll;
    }

    protected void battle(Unit unit){
        messageCallback.send(getName() + " engaged in combat with " + unit.getName() + ".");
        messageCallback.send(describe());
        messageCallback.send(unit.describe());

        int attackRoll = attack();
        int defenseRoll = unit.defend();

        int penetration = attackRoll - defenseRoll;
        if(penetration > 0)
            unit.health.reduceHealth(penetration);
        else
            penetration = 0;
        messageCallback.send(getName() + " dealt " + penetration + " damage to " + unit.getName() + ".");
    }

    public void interact(Tile tile){
        tile.accept(this);
    }

    public String describe(){
        return String.format("%s\t\tHealth: %s\t\tAttack: %d\t\tDefense: %d", name, health, attack_pts, defense_pts);
    }

    public boolean isDead(){
        return health.isDead();
    }

    public void visit(EmptyTile emptyTile){
        Position temp = position;
        position = emptyTile.position;
        emptyTile.position = temp;
    }

    public void visit(Wall wall) { }

    public abstract void visit(Player player);
    public abstract void visit(Enemy enemy);
    public abstract void accept(Unit unit);
}
