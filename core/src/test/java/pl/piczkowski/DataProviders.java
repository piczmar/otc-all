package pl.piczkowski;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.testng.annotations.DataProvider;

public class DataProviders {
	private static final String stringSample = "you,yielding,years:15,with,winged,which,wherewith,wherein,were,waters.And,waters,waste,was,void;,very,us,upon,unto,under,two,trees,tree,together,to,third,things,thing,thereof,there,them:,them.28,them,their,the,that,swarms,swarmed,swarm,subdue,stars,so.And,so.31,so.25,so.16,sixth,signs,sid,shall,set,seed;,seed,second,seasons,seas,sea-monsters,sea,saying,saw,said,rule,replenish,put,place,own,over,our,open,one,on,of,night;,night:,night,multiply,moveth,moved,morning,midst,man,male,make,made,living,likeness:,lights;,lights,light:,light.And,light,life,let,lesser,land,kind:,kind,its,it;,it,is,in,image,his,him;,herbs,herb,heavens,heaven.21,heaven,he,have,had,ground,green,greater,great,grass,good:,good.And,good.26,good.22,good.19,good.,given,give,gathering,gathered,fruitful,fruit-trees,fruit,from,fourth,forth,for,food:30,food:,fly,fish,firmament:,firmament,fill,fifth,female,face,everything,every,evening,earth:,earth18,earth.And,earth.29,earth.27,earth.23,earth,dry,dominion,divided,divide,deep:,days,day.And,day.24,day.20,day.14,day.,day,darkness:,darkness.And,darkness,creeping,creepeth,creatures,creature,created,cattle,called,brought,bring,blessed,birds,bird,behold,beginning,beasts,beast,bearing,be,appear:,and,also.17,all,after,above,a,Spirit,Seas:,Night.,Let,In,I,Heaven.,God,Earth;,Day,Behold,Be,And,13,1";

	@SuppressWarnings("unused")
	@DataProvider
	private static final Object[][] getIntegers() {

		return new Object[][] { { 
			new Integer[] { 3, 4, 5, 12, 37, 1, 2 }, "37,12,5,4,3,2,1" }, 
			{ new Integer[] { -1, -32, 0, 13, 76, 58 }, "76,58,13,0,-1,-32" }, 
			{ new Integer[] { -24, 100, 600, 202, -1 }, "600,202,100,-1,-24" }

		};
	}

	@SuppressWarnings("unused")
	@DataProvider
	private static final Object[][] getStrings() {
		String[] uniqueStrings = stringSample.split(",");
		Set<String> shuffledStrings = new HashSet<String>(Arrays.asList(uniqueStrings));
		return new Object[][] { { shuffledStrings.toArray(new String[] {}), stringSample } };
	}

}
