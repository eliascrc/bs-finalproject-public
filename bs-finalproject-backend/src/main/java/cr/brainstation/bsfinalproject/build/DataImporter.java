package cr.brainstation.bsfinalproject.build;

import cr.brainstation.bsfinalproject.model.Product;
import cr.brainstation.bsfinalproject.enums.ProductCategory;
import cr.brainstation.bsfinalproject.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * Class in charge of creating the products that are needed as dummy data in the application.
 */
@Component
public class DataImporter {

    private final ProductService productService;

    @Autowired
    public DataImporter(ProductService productService) {
        this.productService = productService;
    }

    /**
     * Creates 36 products in the database with all required attributes. Uses an static s3 url with the image.
     */
    public void importProductsData() {
        // ID : 1
        Product product = new Product();
        product.setName("hass avocado");
        product.setAmount("1 unit");
        product.setDescription("These gorgeous Hass avocados are shipped to you ready to eat! Don't delay in enjoying the buttery texture and rich, slightly sweet flavor. We love to plump up burritos, sandwiches and salads with these \"butter pears,\" but they also make a satisfying addition to health smoothies to start your day off right.");
        product.setPrice(new BigDecimal(3.99));
        product.setInStock(30);
        product.setCategory(ProductCategory.FRUIT);
        product.setImageUrl("https://s3.us-east-2.amazonaws.com/bs-finalproject-uploads/public/products/1.jpg");

        product.setFeatured(true);
        this.productService.create(product);

        // ID : 2
        product = new Product();
        product.setName("asparagus");
        product.setAmount("1 lb");
        product.setDescription("Sweet, delicate, and grassy-green, with full-flavored earthiness, asparagus is a true taste of spring. Their heft stands up well to stronger seasonings, higher heat, and longer cooking, and they pack more flavor per inch.");
        product.setPrice(new BigDecimal(3.99));
        product.setInStock(30);
        product.setCategory(ProductCategory.VEGETABLE);
        product.setImageUrl("https://s3.us-east-2.amazonaws.com/bs-finalproject-uploads/public/products/2.jpg");

        product.setFeatured(false);
        this.productService.create(product);

        // ID : 3
        product = new Product();
        product.setName("green beans");
        product.setAmount("1 lb");
        product.setDescription("Herbal and earthy, firm and crisp, green beans are universal favorites. They are a classic side dish served on their own — simply steam, sauté, or boil them (we like to add a little butter and lemon). Their texture also stands up to long cooking in dishes like casseroles and stews. We always buy a few more than we need, because we tend to nibble on them raw as we're preparing them.");
        product.setPrice(new BigDecimal(2.49));
        product.setInStock(30);
        product.setCategory(ProductCategory.BEAN);
        product.setImageUrl("https://s3.us-east-2.amazonaws.com/bs-finalproject-uploads/public/products/3.jpg");

        product.setFeatured(false);
        this.productService.create(product);

        // ID : 4
        product = new Product();
        product.setName("snow peas");
        product.setAmount("1 lb");
        product.setDescription("Sweet and crisp, with a nice little snap of sharpness and great crunch. Snow peas are most familiar in Asian dishes. \"Snow pea\" is almost a misnomer, since most of what you're enjoying is not the flat little \"pea\" inside but the chewy-crisp pod. Don't cook them through or they will turn flabby. Just heat them gently (sauté or steam) for less than a minute.");
        product.setPrice(new BigDecimal(3.99));
        product.setInStock(30);
        product.setCategory(ProductCategory.BEAN);
        product.setImageUrl("https://s3.us-east-2.amazonaws.com/bs-finalproject-uploads/public/products/4.jpg");

        product.setFeatured(true);
        this.productService.create(product);

        // ID : 5
        product = new Product();
        product.setName("black beans");
        product.setAmount("1/2 lb");
        product.setDescription("Grown at Ladybug’s Mew in Yellow Point. An established farm in Yellow Point – producing pesticide free berries (Cascade berries, strawberries, raspberries and blueberries), vegetables, herbs and a variety of handcrafted items (soaps and sachets, etc.) utilizing herbs and flowers from our 12.7 acre paradise. FD (Farmer Doug) and TOF (The Other Farmer) are often asked “what is a Cascade Berry?” It is a tender, tart and juicy berry… believed to be a cross between a Loganberry and a wild Oregon blackberry, although much larger and more prolific.");
        product.setPrice(new BigDecimal(5.20));
        product.setInStock(30);
        product.setCategory(ProductCategory.BEAN);
        product.setImageUrl("https://s3.us-east-2.amazonaws.com/bs-finalproject-uploads/public/products/5.jpg");

        product.setFeatured(false);
        this.productService.create(product);

        // ID : 6
        product = new Product();
        product.setName("carrots");
        product.setAmount("2 lb");
        product.setDescription("This product is grown locally by the Farmship Growers Co-operative located at 13188 Doole Road, Ladysmith, BC, V9G 1G6. The Farmship Growers Co-operative pledges to grow as much ethical, healthy, and organic produce for our community as possible and to encourage and promote a food resilliant local economy. Thier mission is to materialize a dynamic and regenerative community farm and market garden. The value of our group as a growers cooperative is to rapidly create more opportunities in local agriculture and to advocate our communities food security. ");
        product.setPrice(new BigDecimal(7.80));
        product.setInStock(30);
        product.setCategory(ProductCategory.VEGETABLE);
        product.setImageUrl("https://s3.us-east-2.amazonaws.com/bs-finalproject-uploads/public/products/6.jpg");

        product.setFeatured(false);
        this.productService.create(product);

        // ID : 7
        product = new Product();
        product.setName("potatoes");
        product.setAmount("2 lb");
        product.setDescription("This product is grown locally by the Farmship Growers Co-operative located at 13188 Doole Road, Ladysmith, BC, V9G 1G6. The Farmship Growers Co-operative pledges to grow as much ethical, healthy, and organic produce for our community as possible and to encourage and promote a food resilliant local economy. Thier mission is to materialize a dynamic and regenerative community farm and market garden. The value of our group as a growers cooperative is to rapidly create more opportunities in local agriculture and to advocate our communities food security. ");
        product.setPrice(new BigDecimal(7.80));
        product.setInStock(30);
        product.setCategory(ProductCategory.VEGETABLE);
        product.setImageUrl("https://s3.us-east-2.amazonaws.com/bs-finalproject-uploads/public/products/7.jpg");

        product.setFeatured(false);
        this.productService.create(product);

        // ID : 8
        product = new Product();
        product.setName("sweet onions");
        product.setAmount("1 lb");
        product.setDescription("This product is grown locally by the Farmship Growers Co-operative located at 13188 Doole Road, Ladysmith, BC, V9G 1G6. The Farmship Growers Co-operative pledges to grow as much ethical, healthy, and organic produce for our community as possible and to encourage and promote a food resilliant local economy. Thier mission is to materialize a dynamic and regenerative community farm and market garden. The value of our group as a growers cooperative is to rapidly create more opportunities in local agriculture and to advocate our communities food security. ");
        product.setPrice(new BigDecimal(3.95));
        product.setInStock(30);
        product.setCategory(ProductCategory.VEGETABLE);
        product.setImageUrl("https://s3.us-east-2.amazonaws.com/bs-finalproject-uploads/public/products/8.jpg");

        product.setFeatured(false);
        this.productService.create(product);

        // ID : 9
        product = new Product();
        product.setName("kabocha squash");
        product.setAmount("1 lb");
        product.setDescription("Grown at Ladybug’s Mew in Yellow Point. An established farm in Yellow Point – producing pesticide free berries (Cascade berries, strawberries, raspberries and blueberries), vegetables, herbs and a variety of handcrafted items (soaps and sachets, etc.) utilizing herbs and flowers from our 12.7 acre paradise. FD (Farmer Doug) and TOF (The Other Farmer) are often asked “what is a Cascade Berry?” It is a tender, tart and juicy berry… believed to be a cross between a Loganberry and a wild Oregon blackberry, although much larger and more prolific");
        product.setPrice(new BigDecimal(1.69));
        product.setInStock(30);
        product.setCategory(ProductCategory.VEGETABLE);
        product.setImageUrl("https://s3.us-east-2.amazonaws.com/bs-finalproject-uploads/public/products/9.jpg");

        product.setFeatured(false);
        this.productService.create(product);

        // ID : 10
        product = new Product();
        product.setName("brussel sprouts");
        product.setAmount("1 lb");
        product.setDescription("Delicate, earthy flavor with hints of nuttiness. These hearty little green nuggets pack loads of healthful fiber and antioxidants, with a tiny calorie count. Not everyone agrees that Brussels sprouts originated in Brussels. We do know, though, that the fields of Belgium are full of them. Belgians fancy them baked with peeled chestnuts. Try sautéing them in butter with shallots and herbs, roasting them with maple syrup, or simply steaming them.");
        product.setPrice(new BigDecimal(3.99));
        product.setInStock(30);
        product.setCategory(ProductCategory.VEGETABLE);
        product.setImageUrl("https://s3.us-east-2.amazonaws.com/bs-finalproject-uploads/public/products/10.jpg");

        product.setFeatured(false);
        this.productService.create(product);

        // ID : 11
        product = new Product();
        product.setName("bok choy");
        product.setAmount("2 lb");
        product.setDescription("The prettiest cabbage. The crisp, juicy stalks have a mild sweet flavor with a hint of mustard. The supple leaves have an understated fresh garden flavor and texture. Stir-fry the stalks with shrimp, duck, or lamb, then toss with brown rice. Or cut the leaves into thin strips and add them to a clear soup two minutes before serving.");
        product.setPrice(new BigDecimal(3.98));
        product.setInStock(30);
        product.setCategory(ProductCategory.VEGETABLE);
        product.setImageUrl("https://s3.us-east-2.amazonaws.com/bs-finalproject-uploads/public/products/11.jpg");

        product.setFeatured(false);
        this.productService.create(product);

        // ID : 12
        product = new Product();
        product.setName("green cabbage");
        product.setAmount("2 lb");
        product.setDescription("The all-time favorite cabbage. It sets the standard. Firmly packed, with smooth, uniformly green skin. The crisp and fleshy leaves are loaded with tart tanginess and a surprisingly pleasing aroma. Green cabbage is loaded with vitamins and antioxidants. Universally popular, because there is so much you can do with it. Tightly wrapped and refrigerated, it stays fresh for a week or longer.");
        product.setPrice(new BigDecimal(2.98));
        product.setInStock(30);
        product.setCategory(ProductCategory.VEGETABLE);
        product.setImageUrl("https://s3.us-east-2.amazonaws.com/bs-finalproject-uploads/public/products/12.jpg");

        product.setFeatured(false);
        this.productService.create(product);

        // ID : 13
        product = new Product();
        product.setName("celery");
        product.setAmount("1 lb");
        product.setDescription("A staple in any cook's crisper drawer. Celery is a versatile ingredient with a deep clean taste. It puts the crunch in chicken salad and it's the crispiest dip accompaniment. A sturdy vehicle for peanut butter. When sautéed, celery provides a gentle, herby backbone for soups and stews.");
        product.setPrice(new BigDecimal(3.49));
        product.setInStock(30);
        product.setCategory(ProductCategory.VEGETABLE);
        product.setImageUrl("https://s3.us-east-2.amazonaws.com/bs-finalproject-uploads/public/products/13.jpg");

        product.setFeatured(true);
        this.productService.create(product);

        // ID : 14
        product = new Product();
        product.setName("red onion");
        product.setAmount("1 lb");
        product.setDescription("Add crunch and punch to sandwiches, salads, and burgers with these sweet, colorful onions. We relish them raw \u0097 they also add a mellow moistness and flavor to frittatas, pasta primavera, and stir-fries. It's hard to imagine a summer kitchen without a basket of these purplish-red beauties.");
        product.setPrice(new BigDecimal(1.04));
        product.setInStock(30);
        product.setCategory(ProductCategory.VEGETABLE);
        product.setImageUrl("https://s3.us-east-2.amazonaws.com/bs-finalproject-uploads/public/products/14.jpg");

        product.setFeatured(false);
        this.productService.create(product);

        // ID : 15
        product = new Product();
        product.setName("white onion");
        product.setAmount("1 lb");
        product.setDescription("Add crunch and punch to sandwiches, salads, and burgers with these sweet, colorful onions. We relish them raw \u0097 they also add a mellow moistness and flavor to frittatas, pasta primavera, and stir-fries. It's hard to imagine a summer kitchen without a basket of these purplish-red beauties.");
        product.setPrice(new BigDecimal(1.49));
        product.setInStock(30);
        product.setCategory(ProductCategory.VEGETABLE);
        product.setImageUrl("https://s3.us-east-2.amazonaws.com/bs-finalproject-uploads/public/products/15.jpg");

        product.setFeatured(false);
        this.productService.create(product);

        // ID : 16
        product = new Product();
        product.setName("fuji apple");
        product.setAmount("1 lb");
        product.setDescription("There's a hint of sweet vanilla in this baseball-sized apple. Originally grown in Japan, the Fuji ripens slowly and is a challenge to pick. Which is why some markets charge sky-high Tokyo prices for this crunchy, honey-yellow-fleshed fruit. Fujis retain their flavor and shape when baked.");
        product.setPrice(new BigDecimal(2.00));
        product.setInStock(30);
        product.setCategory(ProductCategory.FRUIT);
        product.setImageUrl("https://s3.us-east-2.amazonaws.com/bs-finalproject-uploads/public/products/16.jpg");

        product.setFeatured(true);
        this.productService.create(product);

        // ID : 17
        product = new Product();
        product.setName("green apple");
        product.setAmount("1 lb");
        product.setDescription("The tartness of a Granny Smith piques your palate, but then its deep sweetness comes out to balance the flavor. It's one of the few apples that stays autumn-crisp almost all year long. A prime eating apple that holds its shape and flavor in pies.");
        product.setPrice(new BigDecimal(2.50));
        product.setInStock(30);
        product.setCategory(ProductCategory.FRUIT);
        product.setImageUrl("https://s3.us-east-2.amazonaws.com/bs-finalproject-uploads/public/products/17.jpg");

        product.setFeatured(false);
        this.productService.create(product);

        // ID : 18
        product = new Product();
        product.setName("baby bananas");
        product.setAmount("10-16 units");
        product.setDescription("The banana is an anytime, year-round snack. We like them fully yellow with just a dusting of brown freckles. But super-ripe, meltingly sweet bananas and firmer greenish ones have their fans too. Slice them onto cereal or pancakes, fold into fruit salad, blend into smoothies, and bake into muffins. Heat brings out bananas' creamy sweetness. Try baking, broiling, or sautéing them with butter and sugar for a luscious dessert.");
        product.setPrice(new BigDecimal(3.99));
        product.setInStock(30);
        product.setCategory(ProductCategory.FRUIT);
        product.setImageUrl("https://s3.us-east-2.amazonaws.com/bs-finalproject-uploads/public/products/18.jpg");

        product.setFeatured(false);
        this.productService.create(product);

        // ID : 19
        product = new Product();
        product.setName("blueberries");
        product.setAmount("18 oz");
        product.setDescription("Smooth-skinned, perfect little globes of fresh, juicy flavor, mostly sweet and a little tart. These plump blueberries have it all: longevity, health benefits, and versatility. Sprinkle them with cream and sugar, scatter them over ice cream, or put three in your martini.");
        product.setPrice(new BigDecimal(12.99));
        product.setInStock(30);
        product.setCategory(ProductCategory.FRUIT);
        product.setImageUrl("https://s3.us-east-2.amazonaws.com/bs-finalproject-uploads/public/products/19.jpg");

        product.setFeatured(false);
        this.productService.create(product);

        // ID : 20
        product = new Product();
        product.setName("red cherries");
        product.setAmount("1 lb");
        product.setDescription("We dare you to eat just one of these firm, sweet, juicy, slightly tart fruits. We select the premium extra-large Bing cherries for their off-the-charts irresistibility. Their shine and juiciness work hand in hand to keep you dipping into the fruit bowl until they're all gone. For a change of pace from cobblers and pie, try them with game and in rich sauces.");
        product.setPrice(new BigDecimal(9.99));
        product.setInStock(30);
        product.setCategory(ProductCategory.FRUIT);
        product.setImageUrl("https://s3.us-east-2.amazonaws.com/bs-finalproject-uploads/public/products/20.jpg");

        product.setFeatured(true);
        this.productService.create(product);

        // ID : 21
        product = new Product();
        product.setName("strawberries");
        product.setAmount("32 oz");
        product.setDescription("Driscoll's strawberries are consistently the best, sweetest, juiciest strawberries available. The large size is convenient for preparing delicious, healthy treats for the entire family or for using daily for healthy smoothies for one. Driscoll's is passionate about high-quality, premium fresh berries. Look for Driscoll's strawberries, blueberries, raspberries and blackberries — fresh berries are all we do! (from Driscoll's)");
        product.setPrice(new BigDecimal(8.99));
        product.setInStock(30);
        product.setCategory(ProductCategory.FRUIT);
        product.setImageUrl("https://s3.us-east-2.amazonaws.com/bs-finalproject-uploads/public/products/21.jpg");

        product.setFeatured(false);
        this.productService.create(product);

        // ID : 22
        product = new Product();
        product.setName("lemons");
        product.setAmount("2 lb");
        product.setDescription("Lots of juice and a bright, clear, tart flavor that is suprisingly low in acid. The rind has lots of tang with a bitter note thrown in. We use lemons as a substitute for salt on veggies, a pinch hitter for vinegar in dressings, and an overall flavor booster in both sweet and savory dishes.");
        product.setPrice(new BigDecimal(5.99));
        product.setInStock(30);
        product.setCategory(ProductCategory.FRUIT);
        product.setImageUrl("https://s3.us-east-2.amazonaws.com/bs-finalproject-uploads/public/products/22.jpg");

        product.setFeatured(false);
        this.productService.create(product);

        // ID : 23
        product = new Product();
        product.setName("navel orange");
        product.setAmount("6 units");
        product.setDescription("Extra-big, beautiful, seedless, very low in acid and filled with mild, sweet flesh. These beauties are supremely simple to peel and section. Bursting with freshly picked juiciness, this is the perfect orange to serve to kids. We also like to toss sections into fruit salad.");
        product.setPrice(new BigDecimal(2.78));
        product.setInStock(30);
        product.setCategory(ProductCategory.FRUIT);
        product.setImageUrl("https://s3.us-east-2.amazonaws.com/bs-finalproject-uploads/public/products/23.jpg");

        product.setFeatured(false);
        this.productService.create(product);

        // ID : 24
        product = new Product();
        product.setName("limes");
        product.setAmount("5 units");
        product.setDescription("Tangy, clean-flavored, and filled with juice and pulp. These aromatic fruits are more tart and bracing than lemons. Kitchen staples in Mexican and Southeast Asian cuisine, limes are key for margaritas, mojitoes, ceviche (citrus-marinated raw fish) and tangy, meringue-topped lime pies.");
        product.setPrice(new BigDecimal(4.00));
        product.setInStock(30);
        product.setCategory(ProductCategory.FRUIT);
        product.setImageUrl("https://s3.us-east-2.amazonaws.com/bs-finalproject-uploads/public/products/24.jpg");

        product.setFeatured(true);
        this.productService.create(product);

        // ID : 25
        product = new Product();
        product.setName("heart grapes");
        product.setAmount("1 lb");
        product.setDescription("Next time the kids are craving candy, hand them a bowl of seedless Candy Heart grapes. Plump, red, and bursting with juice, they taste a little like raspberry lemonade and are just as refreshing. So go ahead and eat your heart out!");
        product.setPrice(new BigDecimal(4.99));
        product.setInStock(30);
        product.setCategory(ProductCategory.FRUIT);
        product.setImageUrl("https://s3.us-east-2.amazonaws.com/bs-finalproject-uploads/public/products/25.jpg");

        product.setFeatured(false);
        this.productService.create(product);

        // ID : 26
        product = new Product();
        product.setName("green seedless grapes");
        product.setAmount("1 lb");
        product.setDescription("Juicy and snappy, with a beautiful balance of sweet and tart. This is the grape that all others are compared to. Rinse and eat them right off the vine for a healthy, refreshing snack any time of day. Freeze them in summer and toss them in drinks!");
        product.setPrice(new BigDecimal(3.99));
        product.setInStock(30);
        product.setCategory(ProductCategory.FRUIT);
        product.setImageUrl("https://s3.us-east-2.amazonaws.com/bs-finalproject-uploads/public/products/26.jpg");

        product.setFeatured(false);
        this.productService.create(product);

        // ID : 27
        product = new Product();
        product.setName("turrialba cheese");
        product.setAmount("16 oz");
        product.setDescription("Made in Costa Rica for FreshDirect by artisans, this luscious log is at once refreshingly light, salty, and smooth. Moist and flavorful with a silky sheen texture, our creamy mozzarella melts on your tongue into a fresh, clean finish. It's perfect paired with prosciutto in sandwiches or generously layered on top of baked ziti, but we especially love it in a Caprese salad.");
        product.setPrice(new BigDecimal(6.99));
        product.setInStock(30);
        product.setCategory(ProductCategory.DAIRY);
        product.setImageUrl("https://s3.us-east-2.amazonaws.com/bs-finalproject-uploads/public/products/27.jpg");

        product.setFeatured(false);
        this.productService.create(product);

        // ID : 28
        product = new Product();
        product.setName("milk");
        product.setAmount("1 bottle");
        product.setDescription("From our community of family farms, we bring you organic grass fed dairy products. Since we started in 2001 it was our goal to offer a Certified Organic line of milk in returnable glass bottles. This was achieved in 2002 when we received our certification and started selling our milk throughout the Mid-Atlantic region. Our Trickling Springs Organic farmers hold high standards in the areas of animal welfare, land sustainability, and milk quality. (from Trickling Springs)");
        product.setPrice(new BigDecimal(6.49));
        product.setInStock(30);
        product.setCategory(ProductCategory.DAIRY);
        product.setImageUrl("https://s3.us-east-2.amazonaws.com/bs-finalproject-uploads/public/products/28.jpg");

        product.setFeatured(false);
        this.productService.create(product);

        // ID : 29
        product = new Product();
        product.setName("white egss");
        product.setAmount("1 dozen");
        product.setDescription("The free-roaming hens of Nature's Yoke live in Lancaster County, PA, eat a 100% vegetarian diet and are given no hormones or antibiotics.");
        product.setPrice(new BigDecimal(3.99));
        product.setInStock(30);
        product.setCategory(ProductCategory.POULTRY);
        product.setImageUrl("https://s3.us-east-2.amazonaws.com/bs-finalproject-uploads/public/products/29.jpg");

        product.setFeatured(true);
        this.productService.create(product);

        // ID : 30
        product = new Product();
        product.setName("white egss");
        product.setAmount("1 dozen");
        product.setDescription("As family farmers, we recognize the impact our farming techniques have on both the hens and the land. On our fifth-generation family farm in Pennsylvania, our hens are fed a 100% vegetarian, natural diet and given free space to roam, roost, and nest. Lovingly cared for in a natural environment, our hens don't need antibiotics or medication. (from Alderfer Eggs)");
        product.setPrice(new BigDecimal(3.49));
        product.setInStock(30);
        product.setCategory(ProductCategory.POULTRY);
        product.setImageUrl("https://s3.us-east-2.amazonaws.com/bs-finalproject-uploads/public/products/30.jpg");

        product.setFeatured(false);
        this.productService.create(product);

        // ID : 31
        product = new Product();
        product.setName("whole chicken");
        product.setAmount("3.8 lb approx");
        product.setDescription("Roasted, fried, grilled, or smoked—a fresh whole chicken is a great way to feed a crowd or family. (Gizzards not included.) Katie's Best is a family-owned and operated business that works almost exclusively with small Amish farms in southern Indiana. The chickens are raised on spacious farms and have free access to the outdoors. The birds are never given antibiotics and are fed an all-vegetable diet of locally sourced Non-GMO Project Verified corn and soybeans. (from Katie's Best)");
        product.setPrice(new BigDecimal(9.84));
        product.setInStock(30);
        product.setCategory(ProductCategory.POULTRY);
        product.setImageUrl("https://s3.us-east-2.amazonaws.com/bs-finalproject-uploads/public/products/31.jpg");

        product.setFeatured(false);
        this.productService.create(product);

        // ID : 32
        product = new Product();
        product.setName("chicken breasts");
        product.setAmount("1.9 lb approx");
        product.setDescription("These thin-sliced boneless skinless breasts are ideal when you want a quick-cooking, small portion of chicken breast. Perfect for sandwiches or cut into strips for stir-fry and fajitas. Nestled in the hills of the Blue Ridge Mountains, Springer Mountain Farms raises chickens that are grown with no antibiotics, steroids, growth stimulants, or hormones. They are fed a locally-grown pesticide-free, all-vegetarian diet, and never given any animal by-products. Springer Mountain Farms have long been American Humane Certified, and they take pride in that. They truly understand that better animal care results in better tasting, higher-quality chicken.");
        product.setPrice(new BigDecimal(8.53));
        product.setInStock(30);
        product.setCategory(ProductCategory.POULTRY);
        product.setImageUrl("https://s3.us-east-2.amazonaws.com/bs-finalproject-uploads/public/products/32.jpg");

        product.setFeatured(false);
        this.productService.create(product);

        // ID : 33
        product = new Product();
        product.setName("delmonico steak");
        product.setAmount("0.5 lb approx");
        product.setDescription("Super-velvety texture and intense flavor. This boneless steak is so flavorful and juicy all it needs is a little salt and pepper and a quick broil. It's a melt-in-your-mouth cut that real steak lovers relish. FreshDirect's 100% Grass-Fed Local Beef program is made up of a group of family-owned farms in the northeast that strictly follow a humane and sustainable agricultural model. The herd is raised without hormones or antibiotics and is exclusively grass-fed, which means they're never given grain or corn. ");
        product.setPrice(new BigDecimal(8.24));
        product.setInStock(30);
        product.setCategory(ProductCategory.MEAT);
        product.setImageUrl("https://s3.us-east-2.amazonaws.com/bs-finalproject-uploads/public/products/33.jpg");

        product.setFeatured(false);
        this.productService.create(product);

        // ID : 34
        product = new Product();
        product.setName("ground beef");
        product.setAmount("1 lb approx");
        product.setDescription("Our fresh, lean ground beef is sourced exclusively for us from the green pastures of Bradford County, PA. Tender, juicy, and chock-full of flavor, it's our favorite ground beef for making burgers, Italian meat sauce, and meatloaf. About Our Packaging: Our ground beef comes in a thermoformed, leak-resistant vacuum package, which locks in the freshness and maintains the exquisite flavor. The reason for the meat's dark purple appearance is its freshness. When opened and exposed to air it will bloom bright red.");
        product.setPrice(new BigDecimal(5.99));
        product.setInStock(30);
        product.setCategory(ProductCategory.MEAT);
        product.setImageUrl("https://s3.us-east-2.amazonaws.com/bs-finalproject-uploads/public/products/34.jpg");

        product.setFeatured(true);
        this.productService.create(product);

        // ID : 35
        product = new Product();
        product.setName("beef ribs");
        product.setAmount("1 lb, 2-3ct per lb");
        product.setDescription("Slow-cook these ribs, and you'll be rewarded with intense flavor and tenderness. Serious beef lovers cherish these extraordinarily juicy chunks of meat for both richness and great value. The lean meat clings to the flavor-packed bone and picks up extra succulence, and the longer you cook them, the deeper the flavor.");
        product.setPrice(new BigDecimal(8.19));
        product.setInStock(30);
        product.setCategory(ProductCategory.MEAT);
        product.setImageUrl("https://s3.us-east-2.amazonaws.com/bs-finalproject-uploads/public/products/35.jpg");

        product.setFeatured(false);
        this.productService.create(product);

    }

}
