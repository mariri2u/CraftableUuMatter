package mariri.craftableuumatter;


import ic2.api.item.Items;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.Property;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler; // used in 1.6.2
//import cpw.mods.fml.common.Mod.PreInit;    // used in 1.5.2
//import cpw.mods.fml.common.Mod.Init;       // used in 1.5.2
//import cpw.mods.fml.common.Mod.PostInit;   // used in 1.5.2
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.registry.GameRegistry;

@Mod(modid="CraftableUuMatterMod", name="Claftable UuMatter", version="1.0.0", dependencies = "required-after:IC2")
@NetworkMod(clientSideRequired=false)
public class CraftableUuMatter {

        // The instance of your mod that Forge uses.
        @Instance(value = "CraftableUuMatterMod")
        public static CraftableUuMatter instance;
        
        // ids
        private int craftValue;
        private int moreRecipeValue;
        private List<Recipe> moreRecipes;
       
        @EventHandler // used in 1.6.2
        //@PreInit    // used in 1.5.2
        public void preInit(FMLPreInitializationEvent event) {
            // Stub Method
            Configuration config = new Configuration(event.getSuggestedConfigurationFile());
	        config.load();
            //Notice there is nothing that gets the value of this property so the expression results in a Property object.
	        Property property;
	        property = config.get(Configuration.CATEGORY_GENERAL, "CellToMatterRate", 5);
	        property.comment = "How many matters crafted by 5 UuMatterCells";
            craftValue = property.getInt();
            
            moreRecipes = new ArrayList<Recipe>();
            moreRecipeValue = config.get(Configuration.CATEGORY_GENERAL, "MoreRecipeValue", 1).getInt();
            for(int i = 0; i < moreRecipeValue; i++){
            	Recipe recipe = new Recipe();
            	property = config.get("MoreRecipe" + (i + 1), "Structure1", " x ");
            	property.comment = "Top Structure to Craft";
            	recipe.setStructure(0, property.getString());
            	property = config.get("MoreRecipe" + (i + 1), "Structure2", "xx ");
            	property.comment = "Middle Structure to Craft";
            	recipe.setStructure(1, property.getString());
            	property = config.get("MoreRecipe" + (i + 1), "Structure3", "   ");
               	property.comment = "Bottom Structure to Craft";
                recipe.setStructure(2, property.getString());
            	recipe.setItemCode(config.get("MoreRecipe" + (i + 1), "ItemCode", 4003).getInt());
            	recipe.setSubItemCode(config.get("MoreRecipe" + (i + 1), "ItemCodeSub", 0).getInt());
              	recipe.setProductValue(config.get("MoreRecipe" + (i + 1), "ProductValue", 5).getInt());
               	moreRecipes.add(recipe);
            }
        	config.save();
        }
       
        @EventHandler // used in 1.6.2
        //@PostInit   // used in 1.5.2
        public void postInit(FMLPostInitializationEvent event) {
                // Stub Method
        	if(craftValue != -1){
        		ItemStack uumattercell =  Items.getItem("UuMatterCell").copy();
        		ItemStack matter = Items.getItem("matter").copy();
        		// Add Recipe: UuMatterCell to Matter
        		matter.stackSize = craftValue;
        		GameRegistry.addRecipe(matter, " x ", "xxx", " x ", 'x', uumattercell);
        		// Add Recipe: Matter & EmptyCell to UuMatterCell
        		ItemStack matterR = Items.getItem("matter").copy();
        		matterR.stackSize = 1;
        		ItemStack cell = Items.getItem("cell").copy();
        		cell.stackSize = 1;
        		ItemStack uumattercellR = Items.getItem("UuMatterCell").copy();
        		uumattercellR.stackSize = 1;
        		switch(craftValue){
        			case 1:
                		GameRegistry.addShapelessRecipe(uumattercellR, matterR, matterR, matterR, matterR, matterR, cell);
                		break;
        			case 2:
                		GameRegistry.addShapelessRecipe(uumattercellR, matterR, matterR, matterR, matterR, cell);
                		break;
        			case 3:
                		GameRegistry.addShapelessRecipe(uumattercellR, matterR, matterR, matterR, cell);
                		break;
        			case 4:
                		GameRegistry.addShapelessRecipe(uumattercellR, matterR, matterR, cell);
                		break;
                	default:
                		uumattercellR.stackSize = (int)(craftValue / 5);
                		GameRegistry.addShapelessRecipe(uumattercellR, matterR, cell);
                		break;
        		}
        		// Add Recipe: Using Matters
        		GameRegistry.addRecipe(new ItemStack(Block.stone, 16), "   ", " x ", "   ", 'x', matter);
        		GameRegistry.addRecipe(new ItemStack(Block.glass, 32), " x ", "x x", " x ", 'x', matter);
        		GameRegistry.addRecipe(new ItemStack(Block.grass, 16), "   ", "x  ", "x  ", 'x', matter);
        		GameRegistry.addRecipe(new ItemStack(Block.cobblestoneMossy, 16), "   ", " x ", "x x", 'x', matter);
        		GameRegistry.addRecipe(new ItemStack(Block.sand, 16), "   ", "  x", " x ", 'x', matter);
        		GameRegistry.addRecipe(new ItemStack(Block.blockSnow, 4), "x x", "   ", "   ", 'x', matter);
        		GameRegistry.addRecipe(new ItemStack(Block.waterStill, 1), "   ", " x ", " x ", 'x', matter);
        		GameRegistry.addRecipe(new ItemStack(Block.lavaStill, 1), " x ", " x ", " x ", 'x', matter);
        		GameRegistry.addRecipe(new ItemStack(Block.oreIron, 2), "x x", " x ", "x x", 'x', matter);
        		GameRegistry.addRecipe(new ItemStack(Block.oreGold, 2), " x ", "xxx", " x ", 'x', matter);
        		GameRegistry.addRecipe(new ItemStack(Block.obsidian, 12), "x x", "x x", "   ", 'x', matter);
        		GameRegistry.addRecipe(new ItemStack(Block.netherrack, 16), "  x", " x ", "x  ", 'x', matter);
        		GameRegistry.addRecipe(new ItemStack(Block.glowStone, 8), " x ", "x x", "xxx", 'x', matter);
        		GameRegistry.addRecipe(new ItemStack(Block.wood, 8), " x ", "   ", "   ", 'x', matter);
        		GameRegistry.addRecipe(new ItemStack(Block.cactus, 48), " x ", "xxx", "x x", 'x', matter);
        		GameRegistry.addRecipe(new ItemStack(Block.vine, 24), "x  ", "x  ", "x  ", 'x', matter);
        		GameRegistry.addRecipe(new ItemStack(Block.cloth, 12), "x x", "   ", " x ", 'x', matter);
        		GameRegistry.addRecipe(new ItemStack(Item.coal, 20), "  x", "x  ", "  x", 'x', matter);
        		GameRegistry.addRecipe(new ItemStack(Item.diamond, 1), "xxx", "xxx", "xxx", 'x', matter);
        		GameRegistry.addRecipe(new ItemStack(Item.redstone, 24), "   ", " x ", "xxx", 'x', matter);
        		GameRegistry.addRecipe(new ItemStack(Item.dyePowder, 9, 4), " x ", " x ", " xx", 'x', matter);
        		GameRegistry.addRecipe(new ItemStack(Item.feather, 32), " x ", " x ", "x x", 'x', matter);
        		GameRegistry.addRecipe(new ItemStack(Item.snowball, 16), "   ", "   ", "xxx", 'x', matter);
        		GameRegistry.addRecipe(new ItemStack(Item.gunpowder, 15), "xxx", "x  ", "xxx", 'x', matter);
        		GameRegistry.addRecipe(new ItemStack(Item.clay, 48), "xx ", "x  ", "xx ", 'x', matter);
        		GameRegistry.addRecipe(new ItemStack(Item.dyePowder, 32, 3), "xx ", "  x", "xx ", 'x', matter);
        		GameRegistry.addRecipe(new ItemStack(Item.dyePowder, 48), " xx", " xx", " x ", 'x', matter);
        		GameRegistry.addRecipe(new ItemStack(Item.reed, 48), "x x", "x x", "x x", 'x', matter);
        		GameRegistry.addRecipe(new ItemStack(Item.flint, 32), " x ", "xx ", "xx ", 'x', matter);
        		GameRegistry.addRecipe(new ItemStack(Item.bone, 32), "x  ", "xx ", "x  ", 'x', matter);
        		GameRegistry.addRecipe(new ItemStack(Items.getItem("resin").getItem(), 21), "x x", "   ", "x x", 'x', matter);
        		GameRegistry.addRecipe(new ItemStack(Items.getItem("iridiumOre").getItem(), 1), "xxx", " x ", "xxx", 'x', matter);
        		GameRegistry.addRecipe(new ItemStack(Block.mycelium, 24), "   ", "x x", "xxx", 'x', matter);
        		GameRegistry.addRecipe(new ItemStack(Block.stoneBrick, 48, 3), "xx ", "xx ", "x  ", 'x', matter);
        		GameRegistry.addRecipe(new ItemStack(Items.getItem("copperOre").getItem(), 5), "  x", "x x", "   ", 'x', matter);
        		GameRegistry.addRecipe(new ItemStack(Items.getItem("tinOre").getItem(), 5), "   ", "x x", "  x", 'x', matter);
        		
        		for(Recipe recipe : moreRecipes){
        			GameRegistry.addRecipe(
        					new ItemStack(recipe.getItemCode(), recipe.getProductValue(), recipe.getSubItemCode()), 
        					recipe.getStructure(0), recipe.getStructure(1), recipe.getStructure(2),
        					'x', matter
        				);
        		}
        	}
        }
        
        @EventHandler // used in 1.6.2
        //@Init       // used in 1.5.2
        public void load(FMLInitializationEvent event) {
        }
}