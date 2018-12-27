-- MySQL dump 10.13  Distrib 5.5.60, for Win64 (AMD64)
--
-- Host: localhost    Database: kids_cademy
-- ------------------------------------------------------
-- Server version	5.5.60

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `animal`
--

DROP TABLE IF EXISTS `animal`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `animal` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `lifespan_minimum` int(11) NOT NULL,
  `lifespan_maximum` int(11) NOT NULL,
  `lifespan_captivity` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_animal_object_id` FOREIGN KEY (`id`) REFERENCES `atlasobject` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `animal`
--

LOCK TABLES `animal` WRITE;
/*!40000 ALTER TABLE `animal` DISABLE KEYS */;
/*!40000 ALTER TABLE `animal` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `app`
--

DROP TABLE IF EXISTS `app`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `app` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `app`
--

LOCK TABLES `app` WRITE;
/*!40000 ALTER TABLE `app` DISABLE KEYS */;
/*!40000 ALTER TABLE `app` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Temporary table structure for view `app_load`
--

DROP TABLE IF EXISTS `app_load`;
/*!50001 DROP VIEW IF EXISTS `app_load`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE TABLE `app_load` (
  `id` tinyint NOT NULL,
  `appId` tinyint NOT NULL,
  `timestamp` tinyint NOT NULL,
  `value` tinyint NOT NULL
) ENGINE=MyISAM */;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `atlasobject`
--

DROP TABLE IF EXISTS `atlasobject`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `atlasobject` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `published` tinyint(1) NOT NULL DEFAULT '1',
  `dtype` varchar(45) NOT NULL,
  `rank` int(11) NOT NULL,
  `name` varchar(45) NOT NULL,
  `display` varchar(45) NOT NULL,
  `description` text NOT NULL,
  `iconPath` varchar(45) NOT NULL,
  `thumbnailPath` varchar(45) NOT NULL,
  `picturePath` varchar(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uq_atlasobject_name` (`name`,`dtype`),
  KEY `fk_atlasobject_user1_idx` (`user_id`),
  CONSTRAINT `fk_atlasobject_user1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=401 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `atlasobject`
--

LOCK TABLES `atlasobject` WRITE;
/*!40000 ALTER TABLE `atlasobject` DISABLE KEYS */;
INSERT INTO `atlasobject` VALUES (321,1,1,'Instrument',1310,'accordion','Accordion','<p>The accordion is a musical instrument that has keys similar to a piano, but is small enough for a person to hold.</p><p>The bellows are the section of cloth, cardboard, leather, and metal located in between keyboards and buttons.</p><p>The bellows are expanded and contracted by the accordionist, which creates vacuum and pressure, driving air through the reed chambers that creates the sound.</p><p>The accordion is used in pop music, folk music, cajun music, jazz, classical, zydeco and dance-pop.</p><p>The accordion has also been referred to affectionately as the squeezebox.</p>','instruments/accordion/icon.jpg','instruments/accordion/thumbnail.png','instruments/accordion/picture.jpg'),(322,1,1,'Instrument',50,'alphorn','Alphorn','<p>The alphorn consists of a wooden natural horn of conical bore, having a wooden cup-shaped mouthpiece</p><p>It is used by mountain dwellers for communication in most mountainous regions of Europe, from the French Alps to the Carpathians.</p><p>The alphorn is carved from solid softwood, generally spruce but sometimes pine.</p><p>In former times the alphorn maker would find a tree bent at the base in the shape of an alphorn, but modern makers piece the wood together at the base.</p><p>A cup-shaped mouthpiece carved out of a block of hard wood is added and the instrument is complete.</p>','instruments/alphorn/icon.jpg','instruments/alphorn/thumbnail.png','instruments/alphorn/picture.jpg'),(323,1,1,'Instrument',439,'bagpipes','Bagpipes','<p>Bagpipes are a wind instrument using enclosed reeds fed from a constant reservoir of air in the form of a bag.</p><p>A set of bagpipes minimally consists of an air supply, a bag, a chanter, and usually at least one drone; anyway, many bagpipes have more than one drone.</p><p>The most common method of supplying air to the bag is through blowing into a blowpipe, or blowstick.</p><p>Before becoming famous in Scotland, the bagpipes had been played for possibly thousands of years in Turkey, Asia, Africa, the Persian Gulf and some places in Europe.</p><p>While commonly used in traditional music, the bagpipes have also been used in less traditional music genres such as metal, hip hop, punks, classical and rock.</p>','instruments/bagpipes/icon.jpg','instruments/bagpipes/thumbnail.png','instruments/bagpipes/picture.jpg'),(324,1,1,'Instrument',95,'balalaika','Balalaika','<p>The balalaika is a Russian stringed musical instrument with a characteristic triangular body and three strings.</p><p>The instrument generally has a short sustain, necessitating rapid strumming or plucking when it is used to play melodies.</p><p>The balalaika family of instruments includes instruments of various sizes, from the highest-pitched to the lowest: the piccolo, prima, secunda, alto, bass and contrabass balalaika.</p><p>The prima balalaika, secunda and alto are played either with the fingers or a plectrum, and the bass and contrabass are played with leather plectra.</p><p>Balalaikas are often used for Russian folk music and dancing.</p>','instruments/balalaika/icon.jpg','instruments/balalaika/thumbnail.png','instruments/balalaika/picture.jpg'),(325,1,1,'Instrument',152,'bandoneon','Bandoneon','<p>The bandoneon is a type of concertina particularly popular in Argentina and Uruguay.</p><p>The bandoneon was invented about 1846 by Heinrich Band, in Krefeld Germany.</p><p>It is an essential instrument in most tango ensembles from the traditional orquesta tipica of the 1910s onwards.</p><p>The bandoneon is held between both hands, with pushing and pulling motions forcing air through its bellows, which is routed through reeds by pressing its buttons.</p><p>The bandoneon was originally intended as an instrument for religious and popular music of the day.</p>','instruments/bandoneon/icon.jpg','instruments/bandoneon/thumbnail.png','instruments/bandoneon/picture.jpg'),(326,1,1,'Instrument',92,'bandura','Bandura','<p>A bandura is an Ukrainian folk instrument, part of plucked string family.</p><p>It combines elements of the zither and lute and was also often referred to by the term kobza.</p><p>The bandura has an oval wooden body and a short, fretless neck attached to the soundboard in an off-centre position.</p><p>The back of a traditional bandura is usually carved from a solid piece of wood.</p><p>Early instruments had 5 to 12 strings. In the XX-th century, the number of strings increased to 56 strings and up to 68 strings on concert instruments.</p><p>The instrument is usually played in a seated position, the body of the instrument held on the lap in a nearly vertical position parallel to the torso.</p>','instruments/bandura/icon.jpg','instruments/bandura/thumbnail.png','instruments/bandura/picture.jpg'),(327,1,1,'Instrument',2010,'banjo','Banjo','<p>The banjo is a four-, five- or six-stringed instrument with a thin membrane stretched over a frame.</p><p>Along the way, it has evolved from its beginnings as a rustic instrument made entirely of natural materials to the modern, metallic instrument.</p><p>The membrane, or head, is typically made of plastic, although animal skin is still occasionally but rarely used, and the frame is typically circular.</p><p>Historically, the banjo occupied a central place in African American traditional music, before becoming popular in the minstrel shows of the XIX-th century.</p><p>The banjo is frequently associated with country, folk and bluegrass music.</p>','instruments/banjo/icon.jpg','instruments/banjo/thumbnail.png','instruments/banjo/picture.jpg'),(328,1,1,'Instrument',162,'bansuri','Bansuri','<p>The bansuri is a transverse flute from a single hollow shaft of bamboo with six or seven finger holes.</p><p>Bansuri is traditionally produced from a special type of bamboo, that naturally grows to long lengths between its nodes, found in Himalayan foothills.</p><p>Bansuri is basically a folk instrument, invariably linked to the lives and playfulness of Krishna. It is a ancient musical instrument associated with cowherds and the pastoral tradition.</p><p>Every bansuri has by design a specific key and tonal center. This key is achieved by variations in length, inner diameter, the relative size and placement of the tap holes. </p><p>Bansuri is typically held horizontally slanting downwards towards right. The index, middle and ring fingers of the right hand cover the outer finger holes, while the same fingers of the left hand cover the rest.</p>','instruments/bansuri/icon.jpg','instruments/bansuri/thumbnail.png','instruments/bansuri/picture.jpg'),(329,1,1,'Instrument',1590,'bass-clarinet','Bass Clarinet','<p>The bass clarinet is a musical instrument of the clarinet family.</p><p>Most modern bass clarinets are straight-bodied, with a small upturned silver-colored metal bell and a curved metal neck.</p><p>The bass clarinet has an appealing, rich, earthy tone quite distinct from other instruments in its range, drawing on and enhancing the qualities of the lower range of the soprano and alto instrument.</p>','instruments/bass-clarinet/icon.jpg','instruments/bass-clarinet/thumbnail.png','instruments/bass-clarinet/picture.jpg'),(330,1,1,'Instrument',5840,'bass-drum','Bass Drum','<p>A bass drum is a large drum that produces a note of low pitch. It is typically cylindrical with the drum\'s diameter much greater than its depth.</p><p>The mallets used for playing the bass drum usually have a thick handle made of beech wood or hornbeam. The head usually has a wood core and is wrapped in felt material.</p><p>Adding the bass pedal to the drum kit made it possible for the drummer to play other percussion instruments such as snare drums and cymbals.</p><p>Early bass drum predecessors were used extensively in war and combat to ensure that the forces were marching in proper step with one another.</p><p>Bass drum usage varies from keeping tempo in marches to rock and roll back beats to melodic voice in conjunction with the other parts of the set in jazz and classic music.</p><p>Most bass drums are stationary, as in the drum kit set up. However when bass drums are used in marching bands they must be moveable and are suspended by straps.</p>','instruments/bass-drum/icon.jpg','instruments/bass-drum/thumbnail.png','instruments/bass-drum/picture.jpg'),(331,1,1,'Instrument',2350,'bass-flute','Bass Flute','<p>The bass flute is the bass member of the flute family. It is in the key of C, pitched one octave below the concert flute.</p><p>Because of the length of its tube, it is usually made with a J shaped head joint, which brings the embouchure hole within reach of the player.</p><p>It is usually only used in flute choirs, as it is easily drowned out by other instruments of comparable register.</p>','instruments/bass-flute/icon.jpg','instruments/bass-flute/thumbnail.png','instruments/bass-flute/picture.jpg'),(332,1,1,'Instrument',6660,'bass-guitar','Bass Guitar','<p>The bass guitar is a stringed instrument played primarily with the fingers or thumb, by plucking, slapping, popping, strumming, tapping, thumping, or picking with a plectrum, often known as a pick.</p><p>The bass guitar is similar in appearance and construction to an electric guitar, but with a longer neck and scale length, and four to six strings or courses.</p><p>Popular music bands and rock groups use the bass guitar as a member of the rhythm section, which provides the chord sequence or progression and sets out the beat for the song.</p>','instruments/bass-guitar/icon.jpg','instruments/bass-guitar/thumbnail.png','instruments/bass-guitar/picture.jpg'),(333,1,1,'Instrument',7,'bucium','Bucium','<p>The bucium is a type of alphorn used by mountain dwellers and by shepherds in Romania and Moldova.</p><p>This is usually 1.5m to 3m in length and made of well-seasoned wood which is conical or cylindrical bored, slit lengthways, hollowed out and then glued together.</p><p>The name is derived from Latin bucinum, originally meaning \'curved horn\', an instrument used by the Romans.</p><p>Bucium is integrated into pastoral life and is used to call the sheep into the sheep-folds in the afternoons and evenings or in the morning when taking the sheep out. </p><p>It is also used for communication between people in the highlands, much the same was in Scandinavia. In some regions bucium lead the funeral processions.</p>','instruments/bucium/icon.jpg','instruments/bucium/thumbnail.png','instruments/bucium/picture.jpg'),(334,1,1,'Instrument',372,'bugle','Bugle','<p>The bugle is one of the simplest brass instruments, having no valves or other pitch-altering devices.</p><p>The first bugles were coil shaped, and were used for hunting purposes. </p><p>The bugle is used mainly in the military where the bugle call is used to indicate the daily routines of camp.</p><p>Historically the bugle was used in the cavalry to relay instructions from officers to soldiers during battle. They were used to assemble the leaders and to give marching orders to the camps.</p><p>While early bugles did not have valves the more modern bugles do have them. Without valves the bugler has to use their lips to change the pitch, and only the harmonic series of notes can be played.</p>','instruments/bugle/icon.jpg','instruments/bugle/thumbnail.png','instruments/bugle/picture.jpg'),(335,1,1,'Instrument',58,'castanets','Castanets','<p>Castanets are a percussion instrument consisting of a pair of concave shells joined on one edge by a string.</p><p>They are held in the hand and used to produce clicks for rhythmic accents or a ripping or rattling sound consisting of a rapid series of clicks.</p><p>Although traditionally castanets were made in the shape of a concave shell, today they are also made in other shapes such as rectangular, square, or even triangular.</p><p>When playing the castanets the musician rests one clapper of the set in the palm of the hand, and links the string around their thumb. </p><p>They use their fingertips to strike the other clapper of the set against the clapper resting in the palm to create sound.</p><p>Dancers use castanets to accompany their dancing but they are being used in other music as well - even in orchestras today.</p>','instruments/castanets/icon.jpg','instruments/castanets/thumbnail.png','instruments/castanets/picture.jpg'),(336,1,1,'Instrument',128,'celesta','Celesta','<p>The celesta is a keyboard instrument that looks similar to an upright piano. The keys connect to hammers that strike a graduated set of metal plates suspended over wooden resonators.</p><p>Although the celesta is a musical instrument from the percussion family it is often included in the keyboard section of an orchestra.</p><p>The sound bars of the celesta are usually made from steel. The steel bars rest on hollow wood resonators. The sound is mellow, warm and rich.</p><p>Instruments with only three octaves do not have pedals but those with four or five octaves usually do have a pedal that serves to dampen or sustain the sound.</p><p>The inventors of the celesta are considered to be Auguste Mustel, a Parisian harmonium, and his father Victor Mustel.</p><p>The celesta is also used in music styles other than orchestras and classical music, like jazz.</p>','instruments/celesta/icon.jpg','instruments/celesta/thumbnail.png','instruments/celesta/picture.jpg'),(337,1,1,'Instrument',3860,'cello','Cello','<p>The cello is a bowed string instrument with four strings tuned in perfect fifths.</p><p>It is a member of the violin family of musical instruments, which also includes the violin and viola.</p><p>The cello is used as a solo musical instrument, as well as in chamber music ensembles, string orchestras, as a member of the string section of symphony orchestras, and some rock bands.</p>','instruments/cello/icon.jpg','instruments/cello/thumbnail.png','instruments/cello/picture.jpg'),(338,1,1,'Instrument',397,'celtic-harp','Celtic Harp','<p>The Celtic harp is a triangular harp traditional to Brittany, Ireland, Scotland and Wales.</p><p>In Ireland and Scotland, it was a wire-strung instrument requiring great skill and long practice to play, and was associated with the Gaelic ruling class.</p><p>In the Republic of Ireland, it appears on the coins and coat of arms.</p><p>Because of the banning of the harp in the 1600s there is not much information about how it was played in Ireland before the ban.</p><p>A harp is played by picking with both hands and each string plays a different note.</p>','instruments/celtic-harp/icon.jpg','instruments/celtic-harp/thumbnail.png','instruments/celtic-harp/picture.jpg'),(339,1,1,'Instrument',50,'cimbalom','Cimbalom','<p>The cimbalom is a concert hammered dulcimer: a type of chordophone composed of a large, trapezoidal box with metal strings stretched across its top.</p><p>The bass strings which are over-spun with copper, are arranged in groups of 3 and are also tuned in unison.</p><p>The instrument name cimbalom also denotes earlier, smaller versions of the cimbalom, and folk cimbaloms, of different tone groupings, string arrangements, and box types.</p>','instruments/cimbalom/icon.jpg','instruments/cimbalom/thumbnail.png','instruments/cimbalom/picture.jpg'),(340,1,1,'Instrument',3,'clapsticks','Clapsticks','<p>Clapsticks are a type of drumstick, percussion mallet or claves that serve to maintain rhythm in Aboriginal voice chants.</p><p>As an ancestral instrument that may traditionally accompany the didgeridoo, it is sometimes referred to as musicsticks.</p><p>The usual technique employed when using clapsticks is to clap the sticks together to create a rhythm that goes along with the song.</p>','instruments/clapsticks/icon.jpg','instruments/clapsticks/thumbnail.png','instruments/clapsticks/picture.jpg'),(341,1,1,'Instrument',1890,'clarinet','Clarinet','<p>The clarinet is a musical instrument family belonging to the group known as the woodwind instruments.</p><p>It has a single-reed mouthpiece, a straight cylindrical tube with an almost cylindrical bore, and a flared bell.</p><p>Clarinets come in different pitches and are classified as such. From highest to lowest they include the piccolo clarinet to the contrabass clarinet .</p><p>The cylindrical bore is primarily responsible for the clarinet\'s distinctive timbre, which varies between its three main registers, known as the chalumeau, clarion and altissmo.</p><p>Clarinets are commonly made of black wood from Africa. The instrument is also sometimes made of Mpingo wood. These trees have wood that resonated well.</p><p>The clarinet became very popular with jazz musicians in the early 1900s. It was an important instrument in this genre well into the 40s in the big band era of music.</p>','instruments/clarinet/icon.jpg','instruments/clarinet/thumbnail.png','instruments/clarinet/picture.jpg'),(342,1,1,'Instrument',3,'cobza','Cobza','<p>The cobza is a multi-stringed instrument of the lute family of folk origin.</p><p>The cobza is metal-strung and has a very short neck without frets, with a bent back pegbox. The back is ribbed. It is usually double or triple strung, and often has a characteristic flat end clasp.</p><p>The origins of the cobza are thought to be a local adaptation of the Persian barbat or Turkish oud.</p>','instruments/cobza/icon.jpg','instruments/cobza/thumbnail.png','instruments/cobza/picture.jpg'),(343,1,1,'Instrument',625,'concertina','Concertina','<p>A concertina is a free-reed musical instrument, like the various accordions and the harmonica.</p><p>It has a bellows, and buttons typically on both ends of it. When pressed, the buttons travel in the same direction as the bellows, unlike accordion buttons, which travel perpendicularly to the bellows.</p><p>The concertina was developed in England and Germany, most likely independently. Various forms of concertina are used for classical music, for the traditional musics of Ireland, England, and South Africa, and for tango and polka music.</p>','instruments/concertina/icon.jpg','instruments/concertina/thumbnail.png','instruments/concertina/picture.jpg'),(344,1,1,'Instrument',824,'cuatro','Cuatro','<p>The cuatro is any of several Latin American instruments of the guitar or lute families. Many cuatros are smaller than a guitar.</p><p>Cuatro means four in Spanish, although current instruments often have more than four strings.</p><p>The cuatro is widely used in ensembles to accompany singing and dancing.</p>','instruments/cuatro/icon.jpg','instruments/cuatro/thumbnail.png','instruments/cuatro/picture.jpg'),(345,1,1,'Instrument',55,'cumbus','Cümbüş','<p>The cümbüş is a Turkish stringed instrument of relatively modern origin.</p><p>It was developed by Zeynel Abidin Cümbüş as an oud-like instrument that could be heard as part of a larger ensemble.</p><p>The cümbüş is shaped like an American banjo, with a spun-aluminum resonator bowl and skin soundboard.</p>','instruments/cumbus/icon.jpg','instruments/cumbus/thumbnail.png','instruments/cumbus/picture.jpg'),(346,1,1,'Instrument',452,'daf','Daf','<p>The daf is a large Persian frame drum used in popular and classical music.</p><p>The frame is usually made of hardwood with many metal ringlets attached, and the membrane is usually goatskin.</p><p>Daf is mostly used to accompany singers and players of the tanbur, violin, oud, saz and other Middle Eastern instruments.</p><p>Some dafs are equipped with small cymbals, making them analogous to a large tambourine.</p>','instruments/daf/icon.jpg','instruments/daf/thumbnail.png','instruments/daf/picture.jpg'),(347,1,1,'Instrument',78,'dan-bau','Đàn bầu','<p>The Đàn bầu is a Vietnamese stringed instrument, in the form of a monochord (one-string) zither.</p><p>While the earliest written records of the Đàn bầu date its origin to 1770s, many scholars estimate its age to be up to one thousand years older than that.</p><p>Traditionally, the Đàn bầu is made of just four parts: a bamboo tube, a wooden rod, a coconut shell half and a silk string.</p>','instruments/dan-bau/icon.jpg','instruments/dan-bau/thumbnail.png','instruments/dan-bau/picture.jpg'),(348,1,1,'Instrument',194,'dhol','Dhol','<p>Dhol can refer to any one of a number of similar types of double-headed drum widely used, with regional variations, throughout the Indian subcontinent.</p><p>The dhol is a double-sided barrel drum played mostly as an accompanying instrument in regional music forms.</p><p>Someone who plays the dhol is known as dholi. Special expression of face and frequent rotation of whole body is associated with a particular dholi to perform a realistic performance.</p>','instruments/dhol/icon.jpg','instruments/dhol/thumbnail.png','instruments/dhol/picture.jpg'),(349,1,1,'Instrument',170,'didgeridoo','Didgeridoo','<p>The didgeridoo is a wind instrument developed by Indigenous Australians.</p><p>It is sometimes described as a natural wooden trumpet or \"drone pipe\".</p><p>Many didgeridoos are painted using traditional or modern paints by either their maker or a dedicated artist.</p>','instruments/didgeridoo/icon.jpg','instruments/didgeridoo/thumbnail.png','instruments/didgeridoo/picture.jpg'),(350,1,1,'Instrument',3740,'double-bass','Double Bass','<p>The double bass is the largest and lowest-pitched bowed string instrument in the modern symphony orchestra.</p><p>The double bass is played either with a bow - arco or by plucking the strings - pizzicato. In orchestral repertoire and tango music, both arco and pizzicato are employed.</p><p>The double bass is a standard member of the orchestra\'s string section, as well as the concert band and is featured in concertos, solo and chamber music in Western classical music.</p>','instruments/double-bass/icon.jpg','instruments/double-bass/thumbnail.png','instruments/double-bass/picture.jpg'),(351,1,1,'Instrument',3000,'drum-kit','Drum Kit','<p>A drum kit is a collection of drums and other percussion instruments set up to be played by a single player.</p><p>The drum kit emerged in the 1800s when a musician in an orchestra was required to play more than one instrument, most often the cymbals, triangle and bass drum.</p><p>The traditional drum kit consists of a mix of drums and idiophones. More recently kits have also included electronic instruments, with both hybrid and entirely electronic kits now in common use.</p><p>The drum kit can be divided into four main sections including the hardware, the extensions, the shells and the breakables.</p><p>Kit drumming consists of two elements: a groove which sets the basic timefeel and drum fills which provide variety and add interest to the drum sound.</p><p>The musician typically uses drum sticks in his hands and pedals at his feet to strike the various instruments.</p>','instruments/drum-kit/icon.jpg','instruments/drum-kit/thumbnail.png','instruments/drum-kit/picture.jpg'),(352,1,1,'Instrument',52,'duduk','Duduk','<p>The duduk is an ancient double-reed woodwind flute made of apricot wood. It is indigenous to Armenia.</p><p>The unflattened reed and cylindrical body produce a sound closer to a clarinet than to more commonly known double-reeds. Unlike other double reed instruments like the oboe or shawm, the duduk has a very large reed proportional to its size.</p><p>UNESCO proclaimed the Armenian duduk and its music as a Masterpiece of the Intangible Heritage of Humanity.</p>','instruments/duduk/icon.jpg','instruments/duduk/thumbnail.png','instruments/duduk/picture.jpg'),(353,1,1,'Instrument',8,'dulcitone','Dulcitone','<p>A dulcitone is a keyboard instrument in which sound is produced by a range of tuning forks, which vibrate when struck by felt-covered hammers activated by the keyboard.</p><p>A significant feature of the dulcitone was its portability, a product of its lightweight and compact construction and the fact that the tuning forks were not prone to going out of tune.</p><p>However, the volume produced is extremely limited, and the dulcitone\'s part is frequently substituted by a glockenspiel.</p>','instruments/dulcitone/icon.jpg','instruments/dulcitone/thumbnail.png','instruments/dulcitone/picture.jpg'),(354,1,1,'Instrument',11,'esraj','Esraj','<p>The esraj is a string instrument found in two forms throughout the north, central, and east regions of India. It is a young instrument by Indian terms, being only about 200 years old.</p><p>The second variant is dilruba, found in the north, where it is used in religious music and light classical songs in the urban areas. Its name is translated as \"robber of the heart\"\".</p><p>The esraj is found in the east and central areas, particularly Bengal and it is used in a somewhat wider variety of musical styles than is the dilruba.</p>','instruments/esraj/icon.jpg','instruments/esraj/thumbnail.png','instruments/esraj/picture.jpg'),(355,1,1,'Instrument',3260,'flute','Flute','<p>The flute is a family of musical instruments in the woodwind group. Unlike woodwind instruments with reeds, a flute is an reedless wind instrument that produces its sound from the flow of air across an opening.</p><p>Flutes are the earliest extant musical instruments. A number of flutes dating to about 43,000 to 35,000 years ago have been found in the Swabian Jura region of present-day Germany.</p><p>These flutes demonstrate that a developed musical tradition existed from the earliest period of modern human presence in Europe.</p>','instruments/flute/icon.jpg','instruments/flute/thumbnail.png','instruments/flute/picture.jpg'),(356,1,1,'Instrument',14,'ghatam','Ghatam','<p>The ghatam is one of the most ancient percussion instruments of South India. It is a clay pot with narrow mouth.</p><p>Although the ghatam is the same shape as an ordinary Indian domestic clay pot, it is made specifically to be played as an instrument.</p><p>The pot is usually placed on the lap of the performer, with the mouth facing the belly. The performer uses the fingers, thumbs, palms, and heels of the hands to strike its outer surface to produce different sounds.</p>','instruments/ghatam/icon.jpg','instruments/ghatam/thumbnail.png','instruments/ghatam/picture.jpg'),(357,1,1,'Instrument',180,'glockenspiel','Glockenspiel','<p>A glockenspiel is a percussion instrument composed of a set of tuned keys arranged in the fashion of the keyboard of a piano.</p><p>The glockenspiel sounds like bells, and the most common glockenspiel used today is played with either wood or plastic mallets.</p><p>Glockenspiel is similar to the xylophone. However, the xylophone\'s bars are made of wood, while the glockenspiel\'s are metal plates or tubes.</p><p>Glockenspiels are quite popular and appear in almost all genres of music ranging from classic to hip-hop and jazz.</p>','instruments/glockenspiel/icon.jpg','instruments/glockenspiel/thumbnail.png','instruments/glockenspiel/picture.jpg'),(358,1,1,'Instrument',174,'goblet-drum','Goblet Drum','<p>The goblet drum is a single head membranophone with a goblet shaped body used mostly in the Middle East, North Africa, South Asia, and Eastern Europe.</p><p>The goblet drum may be played while held under one arm (usually the non-dominant arm) or by placing it sideways upon the lap (with the head towards the player\'s knees) while seated.</p><p>Some drums are also made with strap mounts so the drum may be slung over the shoulder, to facilitate playing while standing or dancing.</p>','instruments/goblet-drum/icon.jpg','instruments/goblet-drum/thumbnail.png','instruments/goblet-drum/picture.jpg'),(359,1,1,'Instrument',20500,'guitar','Guitar','<p>The guitar is a popular musical instrument classified as a string instrument with anywhere from 4 to 18 strings, usually having 6.</p><p>Guitar strings are made of either steel or nylon.</p><p>Acoustic guitars have a variety of parts including the body, the waist, sound hole, bridge, frets, strings, neck, fretboard, tuning pegs and the saddle.</p><p>The electric guitar has many of the same parts as the acoustic guitar and a few more including the headstock, machine heads, nuts, neck, frets, fretboard, pickups, control dials, pick up switch, body and the bridge.</p><p>The guitar is used in a wide variety of musical genres worldwide. It is recognized as a primary instrument in genres such as blues, bluegrass, country, flamenco, folk, jazz, jota, mariachi, metal, punk, reggae, rock, soul, and many forms of pop.</p><p>It is typically played by strumming or plucking the strings with the right hand while fretting the strings with the fingers of the left hand.</p>','instruments/guitar/icon.jpg','instruments/guitar/thumbnail.png','instruments/guitar/picture.jpg'),(360,1,1,'Instrument',35,'guqin','Guqin','<p>The guqin is a plucked seven-string Chinese musical instrument of the zither family.</p><p>It has been played since ancient times, and has traditionally been favoured by scholars as an instrument of great subtlety and refinement.</p><p>The guqin is a very quiet instrument, with a range of about four octaves, and its open strings are tuned in the bass register.</p>','instruments/guqin/icon.jpg','instruments/guqin/thumbnail.png','instruments/guqin/picture.jpg'),(361,1,1,'Instrument',33,'gusli','Gusli','<p>Gusli is the oldest Russian multi-string plucked instrument. Its exact history is unknown.</p><p>It has its relatives throughout the world - kantele in Finland, kannel in Estonia, kanklės and kokle in Lithuania and Latvia. Furthermore, we can find kanun in Arabic countries and the autoharp in the USA.</p><p>It is also related to such ancient instruments as Chinese gu zheng which has a thousand-year history and its Japanese relative koto.</p>','instruments/gusli/icon.jpg','instruments/gusli/thumbnail.png','instruments/gusli/picture.jpg'),(362,1,1,'Instrument',106,'guzheng','Guzheng','<p>The guzheng, also known as Chinese zither, is a Chinese traditional plucked musical string instrument.</p><p>It has 16 strings and movable bridges. The modern guzheng usually has 21 strings with a length of 64 inches.</p><p>There are many formalities used in the playing of the guzheng, including basic plucking actions at the right portion and pressing actions at the left portion, as well as tremolo.</p>','instruments/guzheng/icon.jpg','instruments/guzheng/thumbnail.png','instruments/guzheng/picture.jpg'),(363,1,1,'Instrument',65,'hammered-dulcimer','Hammered Dulcimer','<p>The hammered dulcimer is a percussion instrument and stringed instrument with the strings typically stretched over a trapezoidal sounding board.</p><p>The hammered dulcimer is set before the musician, who may sit cross legged on the floor or on a stool at an instrument set on legs.</p><p>The player holds a small spoon shaped mallet hammer in each hand to strike the strings.</p>','instruments/hammered-dulcimer/icon.jpg','instruments/hammered-dulcimer/thumbnail.png','instruments/hammered-dulcimer/picture.jpg'),(364,1,1,'Instrument',730,'harmonica','Harmonica','<p>The harmonica is a free reed wind instrument used worldwide in many musical genres, notably in blues, American folk music, classical music, jazz, country and rock and roll.</p><p>A harmonica is played by using the mouth to direct air into or out of one or more holes along a mouthpiece. Behind each hole is a chamber containing at least one reed.</p><p>An important technique in performance is bending: causing a drop in pitch by making embouchure adjustments.</p>','instruments/harmonica/icon.jpg','instruments/harmonica/thumbnail.png','instruments/harmonica/picture.jpg'),(365,1,1,'Instrument',181,'harmonium','Harmonium','<p>A harmonium is a keyboard instrument similar to an organ. It blows air through reeds, producing musical notes. The harmonium sounds like an accordion.</p><p>There are two sorts of harmonium. In a foot-pumped harmonium, the player pumps a foot pedal which operates a bellows that sends the air to the reeds.</p><p>A hand-pumped harmonium has a hand bellows that blows the air and only one hand can be used on the keys.</p>','instruments/harmonium/icon.jpg','instruments/harmonium/thumbnail.png','instruments/harmonium/picture.jpg'),(366,1,1,'Instrument',1170,'harp','Harp','<p>The harp is a stringed musical instrument which has a number of individual strings running at an angle to its soundboard.</p><p>Harps vary globally in many ways. In terms of size, many smaller harps can be played on the lap, while larger harps are quite heavy and rest on the floor.</p><p>Harps are usually between two feet and six feet in height. They usually have between 1 and 90 strings.</p><p>The three main parts of a harp include the neck, the sound box, and the strings.</p><p>Some harp frames have a body that was carved from one single piece of wood. Some harps on the other hand have as many as 2000 pieces.</p><p>Different harps may use strings of catgut or nylon, or of metal or some combination.</p><p>A harp is played by picking with both hands and each string plays a different note.</p>','instruments/harp/icon.jpg','instruments/harp/thumbnail.png','instruments/harp/picture.jpg'),(367,1,1,'Instrument',17,'horagai','Horagai','<p>Horagai are large conch shells that have been used as trumpets in Japan for many centuries.</p><p>Unlike most shell trumpets from other parts of the world which produce only one pitch, the Japanese horagai can produce three or five different notes.</p><p>Horagai is used by Buddhist monks for religious purposes. Its use goes back centuries and it is still used today for some rituals.</p>','instruments/horagai/icon.jpg','instruments/horagai/thumbnail.png','instruments/horagai/picture.jpg'),(368,1,1,'Instrument',52,'kalimba','Kalimba','<p>The kalimba is an African musical instrument consisting of a wooden board with attached staggered metal tines, played by holding the instrument in the hands and plucking the tines with the thumbs.</p><p>The kalimba is usually classified as part of the lamellaphone family, and part of the idiophone family of musical instruments.</p><p>Kalimba music, like much of the sub-Saharan African music traditions is based on cross-rhythm. The left hand plays the ostinato bass line, while the right hand plays the upper melody.</p>','instruments/kalimba/icon.jpg','instruments/kalimba/thumbnail.png','instruments/kalimba/picture.jpg'),(369,1,1,'Instrument',172,'koto','Koto','<p>The koto is a traditional Japanese stringed musical instrument similar to the Chinese zheng, the Mongolian yatga, the Korean gayageum, and the Vietnamese đàn tranh.</p><p>Koto are about 180 centimetres length, and made from kiri wood. They have 13 strings that are strung over 13 movable bridges along the width of the instrument, and there is also a 17-string koto variant.</p><p>Players can adjust the string pitches by moving the white bridges in the picture before playing, and use three finger picks to pluck the strings.</p>','instruments/koto/icon.jpg','instruments/koto/thumbnail.png','instruments/koto/picture.jpg'),(370,1,1,'Instrument',1020,'mandolin','Mandolin','<p>A mandolin is a musical instrument in the lute family and is usually plucked with a plectrum. It commonly has four courses of doubled metal strings tuned in unison.</p><p>There are many styles of mandolin, but three are common, the Neapolitan or round-backed mandolin, the carved-top mandolin and the flat-backed mandolin.</p><p>Mandolins are made of a wood body and they have a variety of other parts including the fingerboard, tuners, headstock, posts, nuts, frets, the neck, bridge, tailpiece, sound holes, binding, and strings.</p><p>Much of mandolin development revolved around the soundboard. The soundboard comes in many shapes but generally round or teardrop-shaped, sometimes with scrolls or other projections.</p><p>Mandolins are heard in a variety of music styles today including country, folk, rock, bluegrass, classical, jazz, gospel, blues, old timey, ragtime, and many ethnic music styles as well.</p>','instruments/mandolin/icon.jpg','instruments/mandolin/thumbnail.png','instruments/mandolin/picture.jpg'),(371,1,1,'Instrument',355,'marimba','Marimba','<p>Marimba is a percussion instrument often confused for xylophone but it has a more defined pitch and a more musical sound.</p><p>Marimba has resonators attached to the bars that amplify their sound. The bars are arranged as those of a piano, with the accidentals raised vertically and overlapping the natural bars.</p><p>The pitch of the bars on a marimba is determined by the length of the bar, its thickness, and its density. The width has not effect on the bar\'s pitch when struck.</p><p>The number of bars used to create a marimba depends on what the range will be.</p><p>A xylophones is typically played using hard rubber or wooden mallets whereas for marimba are used soft, yarn-wound ones.</p><p>Marimbas are used in a variety of musical styles and performances including jazz, ensembles, concertos, marching bands, bugle and drum corps, orchestras, and Latin music.</p>','instruments/marimba/icon.jpg','instruments/marimba/thumbnail.png','instruments/marimba/picture.jpg'),(372,1,1,'Instrument',170,'melodica','Melodica','<p>The melodica is a free-reed instrument similar to the pump organ and harmonica.</p><p> It has a musical keyboard on top and is played by blowing air through a mouthpiece from the side of the instrument.</p><p>Although the majority of melodicas are made of plastic, some are made primarily of wood.</p>','instruments/melodica/icon.jpg','instruments/melodica/thumbnail.png','instruments/melodica/picture.jpg'),(373,1,1,'Instrument',53,'mridangam','Mridangam','<p>The mridangam is a percussion instrument from India of ancient origin. It is the primary rhythmic accompaniment in a Carnatic music ensemble.</p><p>The mridangam is a double-sided drum whose body is usually made using a hollowed piece of jackfruit wood about an inch thick.</p><p>During a percussion ensemble, the mridangam is often accompanied by the ghatam, kanjira, and the morsing. The mridangam is nicknamed as the \"King of Percussion\".</p>','instruments/mridangam/icon.jpg','instruments/mridangam/thumbnail.png','instruments/mridangam/picture.jpg'),(374,1,1,'Instrument',1,'mukkuri','Mukkuri','<p>Mukkuri is a traditional Japanese plucked instrument, indigenous to the Ainu. The Mukkuri is made from bamboo and is 10 cm long and 1.5 cm wide.</p><p>Sound is produced by manipulating a string connected to the bamboo reed, and while the instrument is unpitched, tone manipulation can be accomplished by altering the size of one’s mouth, which serves as a resonance box during playing.</p><p>Mukkuri is often accompanied with Tonkori, a plucked string instrument.</p>','instruments/mukkuri/icon.jpg','instruments/mukkuri/thumbnail.png','instruments/mukkuri/picture.jpg'),(375,1,1,'Instrument',836,'oboe','Oboe','<p>Oboes are a family of double reed woodwind musical instruments, usually made of wood. Sound is produced by blowing into the reed and vibrating a column of air.</p><p>The oboe has three main body parts including the bell, lower joint and upper joint, plus its other parts which include the double reed, key, rods, fingerhole and pad.</p><p>Oboes are commonly made from wood such as boxwood, vulcanite, rosewood or granadilla.</p><p>The mouthpiece of the oboe is a double reed with the reeds positioned close together.</p><p>Oboe is commonly used in concert bands, orchestras, chamber music, film music, in some genres of folk music and is occasionally heard in jazz, rock music, pop music and popular music.</p><p>To play the oboe the oboist places the double reed between their lips and they blow. The reeds vibrate and open and close quickly which send energy into the air column. The oboist plays the keys and holes to produce different pitches.</p>','instruments/oboe/icon.jpg','instruments/oboe/thumbnail.png','instruments/oboe/picture.jpg'),(376,1,1,'Instrument',225,'ocarina','Ocarina','<p>The ocarina is an ancient wind musical instrument, a type of vessel flute.</p><p>Variations exist, but a typical ocarina is an enclosed space with four to twelve finger holes and a mouthpiece that projects from the body.</p><p>It is traditionally made from clay or ceramic, but other materials are also used such as plastic, wood, glass, metal or bone.</p><p>Modern ocarina was invented in 1853 by 17-year-old Giuseppe Donati, who also gave it the name ocarina.</p>','instruments/ocarina/icon.jpg','instruments/ocarina/thumbnail.png','instruments/ocarina/picture.jpg'),(377,1,1,'Instrument',58,'panpipe','Panpipe','<p>The panpipe are a group of musical instruments based on the principle of the closed tube, consisting of multiple pipes of gradually increasing length.</p><p>Multiple varieties of panpipe have long been popular as folk instruments. They are considered to be the first mouth organ, ancestor of both the pipe organ and the harmonica.</p><p>The pipes are typically made from bamboo, giant cane, or local reeds. Other materials include wood, plastic, metal and ivory.</p>','instruments/panpipe/icon.jpg','instruments/panpipe/thumbnail.png','instruments/panpipe/picture.jpg'),(378,1,1,'Instrument',12900,'piano','Piano','<p>The piano is a musical instrument of the string family. It was invented based on keyboard technology from pipe organs. </p><p>The piano\'s keyboard is struck by the pianist\'s fingers and thumbs. The keys cause the hammers to strike the strings and create sound.</p><p>Pianos have pedals for the pianist to use including the soft pedal, the sustain pedal and the sostenuto pedal. </p><p>It is widely employed in classical and jazz music for solo and ensemble performances, accompaniment and for composing and rehearsal.</p><p>Although the piano is not portable and often expensive, its versatility and ubiquity have made it one of the world\'s most familiar musical instruments.</p><p>Piano playing technique evolved during the transition from harpsichord and clavichord to fortepiano and continued through the development of the modern piano.</p>','instruments/piano/icon.jpg','instruments/piano/thumbnail.png','instruments/piano/picture.jpg'),(379,1,1,'Instrument',625,'piccolo','Piccolo','<p>The piccolo is a half-size flute, and a member of the woodwind family of musical instruments. The modern piccolo has most of the same fingerings as its larger sibling, the standard transverse flute.</p><p>Although once made of various kinds of wood, glass or ivory, piccolos today are made from a range of materials, including plastic, resin, brass, nickel silver, silver, and a variety of hardwoods.</p><p>Piccolos are often orchestrated to double the violins or the flutes, adding sparkle and brilliance to the overall sound.</p>','instruments/piccolo/icon.jpg','instruments/piccolo/thumbnail.png','instruments/piccolo/picture.jpg'),(380,1,1,'Instrument',741,'pipe-organ','Pipe Organ','<p>The pipe organ is a musical instrument that produces sound by driving pressurized air through organ pipes selected via a keyboard.</p><p>A pipe organ has one or more keyboards played by the hands and a pedalboard played by the feet.</p><p>Some organs have several keyboards - and it is not uncommon for large organs to have five. Keyboards are referred to as \'manuals\'. </p><p>Because each pipe produces a single pitch, the pipes are provided in sets called ranks, each of which has a common timbre and volume throughout the keyboard compass.</p><p>Pipe organs today are commonly found in churches, concert venues, schools and other places where religious, sacred, classical and even modern music is played. </p><p>The sound volume and timbre that is produced in the pipes is controlled by the amount of air supplied to the pipe and the pipe\'s construction.</p>','instruments/pipe-organ/icon.jpg','instruments/pipe-organ/thumbnail.png','instruments/pipe-organ/picture.jpg'),(381,1,1,'Instrument',17,'pungi','Pungi','<p>The pungi is a wind instrument played by snake charmers in India and Pakistan. The instrument consists of a mouth-blown air reservoir which channels air into two reedpipes.</p><p>It is traditionally made from a dried bottle gourd. Often, the neck of the gourd is carved for aesthetic reasons. On the other end, two reed or bamboo pipes are connected.</p><p>The pungi is played with no pauses, with the player employing circular breathing. The pungi originated in India and is still played by snake charmers in street performances.</p>','instruments/pungi/icon.jpg','instruments/pungi/thumbnail.png','instruments/pungi/picture.jpg'),(382,1,1,'Instrument',2430,'saxophone','Saxophone','<p>The saxophone is a family of woodwind instruments used in classical music, chamber music, military bands, marching bands and jazz.</p><p>The saxophone consists of an approximately conical tube, usually of thin brass, flared at the tip to form a bell.</p><p>At intervals along the tube are between 20 and 23 tone holes of varying size and two very small vent holes to assist the playing of the upper register.</p>','instruments/saxophone/icon.jpg','instruments/saxophone/thumbnail.png','instruments/saxophone/picture.jpg'),(383,1,1,'Instrument',50,'shakuhachi','Shakuhachi','<p>The shakuhachi is a Japanese end blown flute. It was originally introduced from China into Japan in the VIII-th century and underwent a resurgence in the early Edo Period.</p><p>Shakuhachi are usually made from the root end of a bamboo culm and are extremely versatile instruments.</p><p>Professional players can produce virtually any pitch they wish from the instrument and play a wide repertoire ranging from Zen music to jazz.</p>','instruments/shakuhachi/icon.jpg','instruments/shakuhachi/thumbnail.png','instruments/shakuhachi/picture.jpg'),(384,1,1,'Instrument',64,'shehnai','Shehnai','<p>The shehnai is a musical instrument similar to the oboe. It is made out of wood, with a double reed at one end and metal or wooden flared bell at the other end.</p><p>Its sound is thought to create and maintain a sense of auspiciousness and sanctity and, as a result, is widely used during marriages, processions and in temples.</p><p>This tubular instrument gradually broadens towards the lower end. It usually has between six and nine holes.</p>','instruments/shehnai/icon.jpg','instruments/shehnai/thumbnail.png','instruments/shehnai/picture.jpg'),(385,1,1,'Instrument',383,'sitar','Sitar','<p>The sitar is a plucked stringed instrument used mainly in Hindustani music and Indian classical music.</p><p>A sitar can have 18 to 21 strings. Six or seven of these are played strings which run over curved, raised frets, and the remainder are sympathetic strings which run underneath the frets and resonate in sympathy with the played strings.</p><p>The sitar is constructed of wood, a gourd for the resonating chamber, camel bone, ebony or deer horn for the bridges. Some sitar model may have two resonating chambers.</p><p>Some sitars today are manufactured from synthetic materials to make them cheaper and faster to produce.</p><p>The instrument is balanced between the player\'s left foot and right knee. The hands move freely without having to carry any of the instrument\'s weight.</p>','instruments/sitar/icon.jpg','instruments/sitar/thumbnail.png','instruments/sitar/picture.jpg'),(386,1,1,'Instrument',912,'snare-drum','Snare Drum','<p>The snare drum is a percussion instrument that produces a sharp staccato sound.</p><p>The snare drum has two heads. The drummer hits the snare drum head with a drum stick or beater that can vary from brushes to the drummer\'s hands. </p><p>Snare drums may be made from various wood, metal, acrylic or composite materials. Marching snare drums are deeper in size than snare drums normally used for orchestral or drum kit purposes.</p><p>Mallets and sticks used for playing the snare drum are thin and tapering, made of wood and sometimes coated in plastic. </p><p>For military music the sticks are thicker and heavy and for other music such as jazz the sticks are thinner - the type of sound dictates the type of stick to be used. </p><p>The snare drum is also referred to as the side drum and is the smallest cylinder drum.</p><p>The snare drum can be found in concert bands, marching bands, parades, drumlines and in rock bands and modern music.</p>','instruments/snare-drum/icon.jpg','instruments/snare-drum/thumbnail.png','instruments/snare-drum/picture.jpg'),(387,1,1,'Instrument',82,'taiko','Taiko','<p>Taiko are a broad range of Japanese percussion instruments. </p><p>In Japanese, the term refers to any kind of drum, but outside Japan, it is used to refer to any of the various Japanese drums called wadaiko and to the form of ensemble taiko drumming, called kumi-daiko.</p><p>Taiko drums usually have a shell with heads on both sides, and the cavity is sealed to provide the resonating effect. Some taiko are tunable and some are not. </p><p>In feudal Japan, taiko were often used to motivate troops, call out orders or announcements and set a marching pace.</p><p>Taiko have been incorporated in Japanese theatre for rhythmic needs, general atmosphere, and in certain settings decoration. For example, scenes may be accompanied by taiko to create dramatic tension.</p><p>The word \'kata\' is used to refer to the movement and posture one uses when playing taiko. When taiko is being judged in performance, kata is the main factor used to distinguish quality.</p>','instruments/taiko/icon.jpg','instruments/taiko/thumbnail.png','instruments/taiko/picture.jpg'),(388,1,1,'Instrument',318,'tambourine','Tambourine','<p>The tambourine is an instrument of the percussion family commonly used in a variety of music from around the world.</p><p>The tambourine consists of a frame with pairs of small metal jingles. It comes in many shapes with the most common being circular.</p><p>Classically the term tambourine denotes an instrument with a drumhead, though some variants may not have a head at all.</p><p>Tambourines today can be found in a variety of music styles including rock and roll, classical, marching bands, pop music and almost every other style imaginable around the world.</p><p>The tambourine is often combined with other instruments such as the snare drum, triangle, tenor drum and bass drum as well as the marimba, wood blocks, castanets, tremolos and a variety of string instruments such as the acoustic guitar. </p><p>Tambourine can be shook, struck, jingled or tapped against the body to produce sound and is commonly played by the lead singer in a band or by the drummer in rock or modern music.</p>','instruments/tambourine/icon.jpg','instruments/tambourine/thumbnail.png','instruments/tambourine/picture.jpg'),(389,1,1,'Instrument',758,'theatre-organ','Theatre Organ','<p>A theatre organ is a distinct type of pipe organ originally developed to provide music and sound effects to accompany silent films.</p><p>Theatre organs are usually identified by the distinctive horseshoe-shaped arrangement of stop tabs above and around the instrument\'s keyboards on their consoles.</p><p>Given their prominent placement in houses of popular entertainment, theatre organ consoles were typically decorated in gaudy ways.</p>','instruments/theatre-organ/icon.jpg','instruments/theatre-organ/thumbnail.png','instruments/theatre-organ/picture.jpg'),(390,1,1,'Instrument',1,'tonkori','Tonkori','<p>The Tonkori is a plucked string instrument played by the Ainu people of Hokkaido, northern Japan and Sakhalin. It generally has five strings, which are not stopped or fretted but simply played open.</p><p>The instrument is typically constructed of a single piece of Jezo spruce approximately a meter long. Its shape is traditionally said to resemble a woman\'s body, and the corresponding words are used for its parts.</p><p>The Tonkori is played angled across the chest, strings outward, while both hands pluck the open strings from opposite sides. The instrument is used to accompany songs or dances, or played solo.</p>','instruments/tonkori/icon.jpg','instruments/tonkori/thumbnail.png','instruments/tonkori/picture.jpg'),(391,1,1,'Instrument',4400,'triangle','Triangle','<p>The triangle is a hand percussion instrument. It is a bar of metal, usually steel but sometimes other metals like beryllium or copper, bent into a triangle shape.</p><p>The manner in which the triangle is suspended is critical to the quality of sound produced. Since the triangle is a highly resonant instrument and must be free to vibrate, a good triangle clip with a very thin suspension line is essential.</p><p>The triangle is often the subject of jokes, considered archetypal instrument that requires no skill to play. </p><p>However, triangle parts in classical music can be very demanding; in the hands of an expert it can be a subtle and expressive instrument that makes a beautiful tone with the vibration.</p><p>While the traditional triangle playing method is to hold it suspended from string or fishing line, others hook it over the hand to allow them to use their fingers to alter the tone.</p>','instruments/triangle/icon.jpg','instruments/triangle/thumbnail.png','instruments/triangle/picture.jpg'),(392,1,1,'Instrument',2790,'trumpet','Trumpet','<p>A trumpet is a musical instrument commonly used in orchestral and jazz ensembles. The trumpet group contains the instruments with the highest register in the brass family.</p><p>Trumpets are played by blowing air through almost-closed lips, producing a buzzing sound that starts a standing wave vibration in the air column inside the instrument.</p><p>Early trumpets did not provide means to change the length of tubing, whereas modern instruments generally have 3 or sometimes 4 valves in order to change their pitch.</p>','instruments/trumpet/icon.jpg','instruments/trumpet/thumbnail.png','instruments/trumpet/picture.jpg'),(393,1,1,'Instrument',960,'tuba','Tuba','<p>The tuba is the largest and lowest-pitched musical instrument in the brass family. Like all brass instruments, sound is produced by vibrating or buzzing the lips into a large cupped mouthpiece.</p><p>An orchestra usually has a single tuba, though an additional tuba may be asked for.</p><p>It serves as the bass of the orchestral brass section and it can reinforce the bass voices of the strings and woodwinds.</p>','instruments/tuba/icon.jpg','instruments/tuba/thumbnail.png','instruments/tuba/picture.jpg'),(394,1,1,'Instrument',2380,'ukulele','Ukulele','<p>The ukulele, sometimes abbreviated to uke, is a member of the lute family of instruments.</p><p>The ukulele originated in the 19th century as a Hawaiian adaptation of the Portuguese machete, a small guitar-like instrument.</p><p>The original ukuleles were made of wood. There are still ukuleles being made of wood today - but there are also many being made of plastic which makes them cheaper for consumers to buy. </p><p>Ukuleles tend to have a figure-eight shape and look similar to an acoustic guitar but some are non-standard. Non-standard ukuleles are often oval and referred to as the \'pineapple\' ukulele. </p><p>Ukulele generally employs four nylon or gut strings or four courses of strings. Some strings may be paired in courses, giving the instrument a total of six or eight strings.</p>','instruments/ukulele/icon.jpg','instruments/ukulele/thumbnail.png','instruments/ukulele/picture.jpg'),(395,1,1,'Instrument',286,'vibraphone','Vibraphone','<p>The vibraphone is an instrument in the percussion family, also referred to as the vibes or the vibraharp. </p><p>The vibraphone is similar to the xylophone in its overall design but it has bars made of aluminum rather than wood.</p><p>The main parts of a vibraphone include the frame, pedal, resonator tubes, vibrator discs, spindles, motor-driven valves and sound bars. The pedal is referred to as the damper pedal. </p><p>The vibraphone also has a sustain pedal similar to that on a piano. With the pedal up, the bars are all damped and produce a shortened sound. With the pedal down, they sound for several seconds.</p><p>The vibraphone is commonly used in jazz music, where it often plays a featured role and was a defining element of the sound of mid-20th century.</p><p>Some vibraphone players use two mallets in each hand instead of using only one in each hand. Some very talented players use five or six mallets to play the vibraphone.</p>','instruments/vibraphone/icon.jpg','instruments/vibraphone/thumbnail.png','instruments/vibraphone/picture.jpg'),(396,1,1,'Instrument',222,'vienna-horn','Vienna Horn','<p>The Vienna horn is a type of musical horn used primarily in Vienna, for playing orchestral or classical music.</p><p>The Vienna horn uses a unique form of double-cylinder valve associated with the Viennese firm Uhlmann known as a pumpenvalve.</p><p>The pumpenvalve allows the air to flow straight when the valves are not actuated. When a valve is engaged, each cylinder redirects the air stream 90 degrees in one bend, lessening the resistance felt by the player.</p>','instruments/vienna-horn/icon.jpg','instruments/vienna-horn/thumbnail.png','instruments/vienna-horn/picture.jpg'),(397,1,1,'Instrument',2130,'viola','Viola','<p>The viola is a bowed and plucked string instrument. It is slightly larger than a violin in size and has a lower and deeper sound than a violin.</p><p>The viola often plays the inner voices in string quartets and symphonic writing, and it is more likely than the first violin to play accompaniment parts.</p><p>Music that is written for the viola differs from that of most other instruments, in that it primarily uses the alto clef, which is otherwise rarely used.</p>','instruments/viola/icon.jpg','instruments/viola/thumbnail.png','instruments/viola/picture.jpg'),(398,1,1,'Instrument',6540,'violin','Violin','<p>The violin is a wooden string instrument in the violin family. It is the smallest and highest-pitched instrument in the family in regular use.</p><p>The violin typically has four strings tuned in perfect fifths, and is most commonly played by drawing a bow across its strings, though it can also be played by plucking the strings.</p><p>A person who makes or repairs violins is called a luthier. The parts of a violin are usually made from different types of wood, and it is usually strung with gut, Perlon or steel strings.</p>','instruments/violin/icon.jpg','instruments/violin/thumbnail.png','instruments/violin/picture.jpg'),(399,1,1,'Instrument',483,'xylophone','Xylophone','<p>The xylophone is a musical instrument with wooden bars that are struck with a mallet to produce sound. </p><p>The wooden bars are arranged similarly to a piano, and each one is a different length, which creates a different sound. </p><p>Concert xylophones have tube resonators below the bars to enhance the tone and sustain. Frames are made of wood or cheap steel tubing; more expensive xylophones feature height adjustment and more stability in the stand.</p><p>Bars of the xylophone are created with different lengths, which produce different sounds. Shorter xylophone bars produce high notes and longer xylophone bars produce lower notes. </p><p>The sound produced from the xylophone depends heavily on the skill of the player. The player stands to play the xylophone and faces the center of the instrument.</p>','instruments/xylophone/icon.jpg','instruments/xylophone/thumbnail.png','instruments/xylophone/picture.jpg'),(400,1,1,'Instrument',145,'zither','Zither','<p>Zither is a musical instrument consisting of a flat wooden soundbox with numerous strings stretched across it, placed horizontally.</p><p>The term zither refers to three specific instruments: the concert zither, its variant the Alpine zither and the chord zither.</p><p>Zithers are played by strumming or plucking the strings with the fingers or sometimes using an accessory called a plectrum.</p>','instruments/zither/icon.jpg','instruments/zither/thumbnail.png','instruments/zither/picture.jpg');
/*!40000 ALTER TABLE `atlasobject` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `atlasobject_aliases`
--

DROP TABLE IF EXISTS `atlasobject_aliases`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `atlasobject_aliases` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `atlasobject_id` int(11) NOT NULL,
  `aliases` varchar(45) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_alias_atlas_object1_idx` (`atlasobject_id`),
  CONSTRAINT `fk_alias_objec_id` FOREIGN KEY (`atlasobject_id`) REFERENCES `atlasobject` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=442 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `atlasobject_aliases`
--

LOCK TABLES `atlasobject_aliases` WRITE;
/*!40000 ALTER TABLE `atlasobject_aliases` DISABLE KEYS */;
INSERT INTO `atlasobject_aliases` VALUES (351,321,'Sun-Fin-Chin'),(352,321,'Bayan'),(353,321,'Trekspill'),(354,321,'Fisarmonica'),(355,322,'Alpenhorn'),(356,322,'Alpine Horn'),(357,323,'Gaida'),(358,325,'Bandonion'),(359,326,'Kobza'),(360,330,'Gran Cassa'),(361,330,'Grosse Caisse'),(362,330,'Grosse Trommel'),(363,330,'Bombo'),(364,332,'Electric Bass'),(366,333,'Trâmbiţă'),(367,333,'Tulnic'),(368,336,'Celeste'),(369,337,'Violoncello'),(370,338,'Telenn'),(371,338,'Cláirseach'),(372,338,'Clàrsach'),(373,338,'Telyn'),(374,339,'Cimbál'),(375,339,'Tsymbaly'),(376,339,'Cymbalum'),(377,339,'Țambal'),(378,340,'Clappers'),(379,340,'Musicsticks'),(380,342,'cobsa'),(381,342,'kobuza'),(382,342,'kobuz'),(383,342,'coboz'),(384,342,'koboz'),(385,347,'dan bau'),(386,349,'Didjeridu'),(387,350,'Bass'),(388,350,'Contrabass'),(389,350,'Upright Bass'),(390,350,'Standup Bass'),(391,350,'Acoustic Bass'),(392,351,'Drum Set'),(393,351,'Trap Set'),(394,354,'Dilruba'),(395,354,'Indian Harp'),(396,358,'Chalice Drum'),(397,358,'Tarabuka'),(398,358,'Darbuka'),(399,358,'Debuka'),(400,358,'Doumbek'),(401,358,'Dumbec'),(402,358,'Dumbeg'),(403,358,'Dumbelek'),(404,358,'Toumperleki'),(405,358,'Tablah'),(406,360,'Qin'),(407,361,'Dilruba'),(408,361,'Indian Harp'),(409,362,'Chinese Zither'),(410,364,'French Harp'),(411,364,'Mouth Organ'),(412,367,'Jinkai'),(413,368,'Thumb Piano'),(414,368,'Mbira'),(415,368,'Likembe'),(416,372,'Pianica'),(417,372,'Blow-organ'),(418,372,'Key Harmonica'),(419,372,'Melodyhorn'),(420,377,'Panflute'),(421,377,'Syrinx'),(422,378,'Grand Piano'),(423,378,'Pianoforte'),(424,381,'Been'),(425,381,'Bin'),(426,382,'Sax'),(427,384,'Shahnai'),(428,384,'Shenai'),(429,384,'Mangal Vady'),(430,386,'Side Drum'),(431,389,'Theater Organ'),(432,389,'Cinema Organ'),(433,394,'Uke'),(435,395,'Vibes'),(436,398,'Fiddle'),(437,332,'Bass'),(438,371,'Marimbaphone'),(439,395,'Vibraharp'),(440,387,'Wadaiko'),(441,387,'Kumi-daiko');
/*!40000 ALTER TABLE `atlasobject_aliases` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `atlasobject_facts`
--

DROP TABLE IF EXISTS `atlasobject_facts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `atlasobject_facts` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `atlasobject_id` int(11) NOT NULL,
  `facts_key` varchar(45) NOT NULL,
  `facts` tinytext NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uq_atlasobject_facts_key` (`atlasobject_id`,`facts_key`),
  KEY `id_atlasobject_facts_object_id` (`atlasobject_id`),
  CONSTRAINT `fk_fact_object1` FOREIGN KEY (`atlasobject_id`) REFERENCES `atlasobject` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=304 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `atlasobject_facts`
--

LOCK TABLES `atlasobject_facts` WRITE;
/*!40000 ALTER TABLE `atlasobject_facts` DISABLE KEYS */;
INSERT INTO `atlasobject_facts` VALUES (120,321,'Most parts are hand made','Accordions are still made with a lot of human hands as opposed to by machinery. Some parts are made by machine but the best are mostly hand made by craftsman.'),(121,321,'Golden Age of the Accordion','The 1900s to the 1960s has been referred to as the \'Golden Age of the Accordion\'.'),(122,321,'First played on Carnegie Hall in 1939','The first to play the accordion in New York City\'s famous Carnegie Hall were Gene von Halberg, Abe Goldman, and Joe Biviano, in 1939.'),(123,321,'Pietro Deiro is the \'Daddy of the Accordion\'','Pietro Deiro, an Italy-born accordionist was known as the \'Daddy of the Accordion\'. He had a career in San Francisco during the vaudeville era and was even signed to RCA Victor Records.'),(124,321,'Can generate long duration sounds','The accordion is able to sustain sound for a much longer time than most other instruments.'),(125,323,'Nero played the bagpipes','Nero, the ancient emperor of Rome, is believed to have played the bagpipes while Ancient Rome burned.'),(126,323,'Ring caps of elephant ivory','Some traditional bagpipes were made with ring caps of elephant ivory that makes it difficult to take the instrument to countries where ivory has been banned.'),(127,323,'Banned bagpipes','In 1560 and in 1746 the bagpipes were banned in Scotland.'),(128,323,'Scotland the Brave','Scotland the Brave is said to be the most commonly played song on the bagpipes.'),(129,323,'The chanter and the drone','The chanter is the melody pipe which can be played by the piper, while the drone or drones provide a constant note.'),(130,327,'The first white banjo player','The first white banjo player to learn from African Americans was Joel Walker Sweeney who lived from 1810 to 1860.'),(131,327,'The five string banjo is the most popular','The five string banjo is the most popular. Some musicians play with fingerpicks and a resonator but many prefer to use just their fingers.'),(132,327,'Popular during the jazz age','Banjos became extremely popular during the jazz age and in the 1940s guitars began to replace them.'),(133,327,'First named \'strum strump\'','The first banjo description referred to the instrument as a \'strum strump\'. An English doctor was in Jamaica when he saw it and wrote about the banjo in his journal in 1687.'),(134,327,'The duelling banjos','The duelling banjos piece in the 1972 movie Deliverance was actually recorded with a guitar and a banjo not two banjos.'),(135,357,'Campanelli means bell','In compositions or scores the glockenspiel is often referred to as \'campanelli\' which is an Italian term meaning \'bell\'.'),(136,357,'Used in marching bands','Marching bands use a type of glockenspiel called the lyra glockenspiel. This instrument is played upright, hung by a strap over of the musician\'s shoulder, and hit with a beater or mallet.'),(137,357,'Bars arranged as a keyboard','Bars on the glockenspiel are arranged from largest to smallest on a keyboard like base but it has two rows of metal bars.'),(138,357,'Two or four mallets','Some musicians use two mallets in each hand, four in total, to play the glockenspiel.'),(139,357,'Bar length determines sound note','Longer bars on the glockenspiel will create a lower note and shorter bars result in higher notes.'),(140,357,'The Magic Flute','In Wolfgang Amadeus Mozart\'s 1791 composition The Magic Flute, the glockenspiel is used for the bird catcher\'s character.'),(141,376,'An octave with four finger holes','The English pendant ocarina, invented in the 1960s by John Taylor, produces an entire octave using just four finger holes.'),(142,376,'Many different styles','There are many different styles of ocarinas varying in shape and the number of holes: transverse or sweet potato, pendants, inline, multi-chambered ocarinas.'),(143,376,'Used on modern orchestral works','Penderecki, Ligeti and Henze are just three of many modern classical composers who have included ocarina parts in their orchestral scores.'),(144,376,'Popular on crossword and quiz','The word \'ocarina\' is popular with crossword- and quiz-compilers because it contains lots of vowels and because most people have heard about but few know what it means.'),(145,325,'Popular for tango dance','Bandoneon became very popular in Argentina at about 1890 when it was found to be extremely conducive to the sounds of tango.'),(146,325,'Ástor Piazzolla','Ástor Piazzolla, the late Argentinean tango composer and performer, was the leading exponent of the bandoneon in the 20th century.'),(147,325,'Difficult to play it','The bandoneon is a complex instrument because most of the buttons produce a different note when played pushing in than when played pulling out.'),(148,326,'Descend from kobza','A precursor to the bandura was the kobza, a three- to eight-string instrument mentioned in Greek literature of the 6th century. '),(150,326,'Played by blind kobzari','The rise of bandura’s popularity began with era of kobzars — travelling players from villages that mastered string instruments. The most intriguing fact about kobzari is that many of them have been blind. '),(151,326,'Beloved by Ukrainian Cossacks','Bandura is well known as an instrument beloved by Ukrainian Cossacks — since the 17th century, '),(152,326,'Censored by Soviet regime','1930s marked the beginning of Soviet repressions, when many kobzars were arrested and murdered. Despite Soviet censure, interest in the bandura increased in the 20th century. '),(153,326,'Bandura on folk festival','Those who want to hear bandura live have a chance to visit Kraina Mriy – a folk festival held every year on Spivoche Pole in Kyiv. '),(157,333,'Specific to mountain regions','In common with the Swiss Alphorn, Slovenian Rog, Serbian bušen, Polish & Ukrainian trembita, Lithuania truba, Estonia and Scandinavian luur it is only found in the mountain regions. '),(158,333,'Produce pure natural harmonic','Because the bucium does not have valves or finger holes it can only play the pitches in the natural harmonic series. '),(159,333,'Different sound than alphorn','The bucium has a timbre that is much brighter than that of the alphorn due to its narrow bore and very minor flare.'),(160,333,'Angels play the bucium','An old mural in Voroneţ Monastery, Romania shows an angel playing a bucium at the onset of the Last Judgment.'),(162,333,'Played by women','In the Romanian Apuseni mountains it is known as tulnic, and is often played by women. '),(163,328,'Divine instrument','The bansuri is revered as Lord Krishna\'s divine instrument and is often associated with Krishna\'s Rasa Lila.'),(165,328,'Bansuri is classical instrument','Great bansuri maestros have further developed the playing technique of the bansuri in a way that the status as a classical instrument was achieved.'),(166,328,'Really difficult to learn playing','Getting sound is the most crucial aspect of playing the bansuri which is the one that makes 99% of potential bansuri players quit from day one.'),(167,328,'Played as the western flute','The Indian bansuri has a mouthpiece that is played in a similar way as the western transverse flute.'),(168,328,'Requires precise finger position','The holes of bansuri have to be precisely covered either fully or half by the phalanxes of the fingers and not by the fingertips.'),(169,341,'Clarinet player is called clarinettist','A musician that plays the clarinet is referred to as a clarinettist.'),(170,341,'Last added to classic orchestra','The last instrument to be added to the symphony orchestra was the clarinet.'),(171,341,'Mozart was the first composer','Wolfgang Amadeus Mozart was the first composer to write a piece of music specifically for the clarinet.'),(172,341,'Piccolo clarinet','The smallest clarinet is the A flat piccolo clarinet and it is very uncommon.'),(173,341,'Clarinet is also used by modern bands','Modern music bands that used the clarinet in some of their music include Tom Waits, Pink Floyd, Aerosmith and The Beatles.'),(174,341,'Well appreciated in Romantic period','During the Romantic period the clarinet and the horn were considered the most important wind instruments. '),(175,330,'As part of a drum kit','The bass drum as part of a drum kit was added in the beginning of the 1900s as part of jazz music.'),(176,330,'Bass pedal invented in 1909','The inventor of striking the bass drum with a bass pedal was William F. Ludwig, in 1909, in Chicago, Illinois.'),(177,330,'Bass drum in orchestra setting','In the orchestra setting the bass drum is suspended in a frame that allows the drummer to change its position or angle. '),(178,330,'Thunderous and rumbling sound','The sounds produced by the bass drum have been described as thunderous, rumbling, pounding, hollow, mighty and even soft at times.'),(179,330,'Bass drum player is called a drummer','Some of the most famous drummers include Neil Peart (from the band RUSH), Buddy Rich, Ringo Star, Phil Collins, Benny Goodman and Rick Allen.'),(180,334,'Used for military signaling','When used in the military the bugle can be an instrument for signalling, but it also associated with being sounded right before charging the enemy.'),(181,334,'Used by military from 1758','The first time a brass bugle was used for military purposes for signalling was in 1758 in Hanover.'),(182,334,'Similar instrument used by Romans','Ancient Romans used the buccina - an instrument similar to the bugle, for military purposes.'),(183,334,'Diverse pitches','Bugle pitches include the high pitch (soprano bugle), medium pitch (alto bugle), tenor pitch (baritone bugle) and bass pitch (contrabass bugle).'),(184,334,'Keyed bugle pattented in 1811','The first keyed bugles were invented in the early 1800s. A patent was granted for the Royal Kent bugle in 1811 to Joseph Halliday in England.'),(185,334,'Not common instrument in orchestras','The bugle\'s limitations have resulted in it not being a common instrument in orchestras or other forms of music and it is most commonly associated with military.'),(186,335,'Women castanets are smaller','Women usually use the smaller castanets as the instrument should fit in the palm of the hand. Men usually use the larger castanets because their hands can accommodate the larger size.'),(187,335,'Smaller castanets produce higher tones','Smaller castanets produce a crisp, higher tone, while larger castanets produce a rich, mellow, lower tone.'),(188,335,'Pulgaretes are attached to thumbs','Castanets are referred to as pulgaretes when they are attached to the dancers\' thumbs.'),(189,335,'Castanets means chestnuts','The word \'castanet\' is derived from the Spanish word castaina which means chestnut.'),(190,335,'Professional castanets are hand maded','Castanets that are handcrafted for professional musicians are made of more expensive materials such as hardwood or composite and cost much more.'),(191,335,'Quality castanets made of woods','Quality castanets are usually made of hardwoods such as oak, pomegranate, ebony, rosewood, and granadillo.'),(192,336,'Played as the piano','Playing the celesta is similar to playing the piano with keys and a pedal. The pedal is used when playing to prolong the notes\' sound.'),(193,336,'Sound similar to glockenspiel','The celesta sound is similar to the glockenspiel but it has a warmer and rounder sound.'),(194,336,'Used for gentle passages','The celesta is normally used for passages that are meant to sound gentle and soft as it produces piercing music when played hard.'),(195,336,'Produces faint sounds','The celesta does not produce loud sound and it does not have the same dynamic range as a piano.'),(196,336,'Dance of the Sugar Plum Fairy','One of the best-known works that uses the celesta is Tchaikovsky\'s \"Dance of the Sugar Plum Fairy\" from The Nutcracker.\n'),(197,336,'Celeste means heavenly','The instrument\'s name celeste means heavenly in the French language and it was given the name because of the soft sound it produces. '),(198,351,'First used by military bands','Drum kits first became popular military bands and then in jazz music.'),(199,351,'Big band swing music','azz music, which included the drum kit, was a big part of the music in the depression era. It was a vital sound of the big band swing music.'),(200,351,'Breakables are interchangeable','When a drum kit is shared between several drummers, which happens if more than one band is playing on stage in one night, the \'breakables\' of the drum kit are usually swapped out between drummers so that they can use their own.'),(201,351,'Complex set of instruments','The main components of the modern drum kit include the snare drum, toms, bass drum, ride cymbals, hi-hats, crashes, effects cymbals and additional instruments such as cowbells, wood blocks, gongs, chimes, triangles, tambourines and tubular bells.'),(202,351,'Different configurations','Drum kits can come in different configurations such as three piece sets, four piece drum kit with floor tom, four piece drum kit with two hanging toms, five piece sets, cocktail kit and extended kit.'),(203,359,'World most popular instrument','In the early 1900s the guitar became the world\'s most popular instrument.'),(204,359,'Used in many genres of music','Classical guitars are commonly the main instrument in several genres of music including country, blues, soul, folk, jazz, mariachi and flamenco.'),(205,359,'Strings from mylon or steel','Classical guitars tend to have nylon strings; acoustic and folk guitars tend to have steel strings; electric guitars have steel strings.'),(206,359,'Electric guitar invented in 1930s','The electric guitar was invented in the 1930s once it was discovered that the amplifier could change guitar tones.'),(207,359,'Play with plectrum','The guitar pic (or plectrum) is commonly used when playing the electric guitar or acoustic guitars fitted with steel strings. Pics are often made of plastic but some are also made of wood, bone or even steel.'),(208,359,'Eletric guitar can have any shape','Electric guitars can have either a solid, semi-solid or hollow body as they do not require a chamber to produce sound and tones.'),(209,366,'Harp with solid body','The electric harp is a relatively new invention. These harps can be hollow body or solid body depending on the design.'),(210,338,'Played with only four fingers','When picking, the harpist only uses the first four fingers on each of their hands.'),(211,338,'Folk harps revived','The Celtic harp tradition has been revived somewhat beginning in the 1900s and folk harps are becoming popular again after having been replaced for so long by the orchestral harp.'),(212,338,'Bannd by English in XVII-th century','In the 1600s when the English ruled in Ireland the harp was banned and many were burned and the harpers were often executed.'),(213,338,'Playing glissando','When a harpist sweeps their hands across the strings this technique is referred to as glissando.'),(214,366,'One of the oldest instrument','The harp is considered to be one of the world\'s oldest musical instruments.'),(215,366,'Playing glissando','When a harpist sweeps their hands across the strings this technique is referred to as glissando.'),(216,366,'Used in a variety of music styles','The harp can be heard in a variety of music styles including classical, orchestras, Celtic, African, rock, bluegrass, country, folk, jazz and many more.'),(217,366,'Pedal for pitch control','Harps can have a pedal which can help to raise the pitch of a string.'),(218,370,'Ease to learn to play','The mandolin is considered one of the easier instruments to learn to play.'),(219,370,'Eight string version is from Naples','The most common mandolin is the eight string version that was originally designed in Naples, Italy.'),(220,370,'Maggie May by Rod Stewart','One of the most famous mandolin pieces in the rock genre is the one in the song Maggie May by Rod Stewart played by Ray Jackson.'),(221,370,'Belongs to lute family','The mandolin is classified as a member of the lute family - not as a type of guitar. '),(222,370,'Popular on Middle Ages','The mandolin was a popular instrument of the Middle Ages. They were very popular in Italian and French classical music.'),(223,370,'Famous composers','Famous composers that included mandolin pieces in their compositions included Beethoven, Vivaldi, Mozart, Handel, John Craton, Paganini, Yasuo Kuwahara and Jiro Nakano.'),(224,371,'Marimba player is marimbist','A musician that plays the marimba is referred to as a marimba player or a marimbist.'),(226,371,'Tuned with wax','When the instrument must be tuned quickly it is possible to add a lump of wax to a bar. Other tuning methods include adding or removing wood or other material. '),(227,371,'Maimba plus phone','In some countries the word \'phone\' is added to marimba and the instrument is then referred to as a marimbaphone.'),(228,371,'Bars rest on pegs','The bars of the marimba rest on pegs that are mounted on the frame which allows them to vibrate when stuck with the mallet.'),(229,371,'Bars made of wood','The bars of the marimba are made of wood and are ordered on the frame according to size. The wood bars have holes drilled in one end to allow for string to be threaded through in order to hold the bars in place.'),(230,371,'Mallets affect produced sound','The mallet used for playing the marimba varies depending on the sound required. The mallet heads are usually rubber, plastic or wood. They are usually wrapped in yarn.'),(231,375,'Viennese oboe','The Viennese oboe is shorter and thicker than the French oboe and widens at the joints. Also, the French oboe\'s bell is slightly flared while the bell of the Viennese oboe has a bell shape to it.'),(232,375,'Oboe player is called oboist','A musician who plays the oboe can be referred to as an oboe player or an oboist.'),(233,375,'Oboe has no mouthpiece','The oboe does not have a mouthpiece like the clarinet. It has two reeds tied together.'),(234,375,'Major oboe composers','Major composers that wrote pieces for the oboe include Handel, Bach, Mozart, Beethoven, Schubert, Wagner, Strauss, Martinu, Schumann and Stravinsky.'),(235,375,'Oboe family','Instruments in the oboe family include the piccolo oboe, regular oboe, oboe d\'amore, cor anglais, bass oboe and heckelphone.'),(236,375,'Difficult to play','The oboe is much more complicated to play than the flute.'),(237,375,'Play treble or soprano range','The most common oboe plays in the treble or soprano range. The distinctive oboe tone is versatile and has been described as bright.\n'),(238,378,'Invetend by Bartolomeo Cristofori','Bartolomeo Cristofori is credited with the invention of the piano. He lived from 1655 to 1731 and was an employee of the Grand Prince of Tuscany as his Instrument Keeper.'),(239,378,'Variety of piano types','There are a variety of piano types today including the grand piano, upright piano, specialized piano the electric piano and the digital piano.'),(240,378,'Keys made of wood','The keys of pianos have been made of a variety of woods over time including sugar pine, spruce, ebony, and basswood.'),(241,378,'White keys covered with ivory','Ivory was used at one time to cover the white keys but has since fallen out of practice for legal and moral issues. Plastic is also commonly used.'),(242,378,'Most expensive piano','The most expensive piano in the world is the Crystal Piano, created in 2008 for the Olympic Games. It later sold for over $3.2 million. '),(243,378,'Piano maestros','Famous pianists include Mozart, Beethoven, Schubert, Billy Joel, Liberace, Jerry Lee Lewis, Elton John, Martha Argerich, Chopin and Myra Hess.\n'),(244,378,'Largest piano in the world','The largest piano in the world was created by Adrian Mann and it weighs 1.4 tons. It is 5.7 meters long. '),(245,378,'King of the Musical Instruments','The piano is capable of the widest range of tones; it can play melody and accompaniment simultaneously; it is the most played instrument in the world. These contribute to it being known as the King of the Musical Instruments. '),(246,380,'Complex invention','Until the telephone was invented the pipe organ was the most complex invention by man.'),(247,380,'Pipe organ playe is called organist','A musician that plays the pipe organ is referred to as an organist. '),(248,380,'Pitch depends on pipe length','Pipe organ pipes are made of metal or wood and the longer the pipe the lower the pitch. '),(249,380,'Play one note at a time','Pipes can only play one note at a time. '),(250,380,'Pipes are arranged into ranks','Pipes are arranged into ranks - which means they are grouped together to enable similarly shaped pipes (and those made of similar materials) can be played together. '),(251,380,'Very large instrument','The pipes of a pipe organ are lined up in rows and can occupy a very large amount of space. The organ case that holds the pipes can be as large as a room. '),(252,380,'Played with hands and feet','In order for a pipe organ to work the organist must use both hands and feet to create sound. '),(253,380,'Oldest pipe organ','The oldest pipe organ in existence today is believed to be the one in Sion, Switzerland, at the Basilica of Valere, built in 1390. '),(254,385,'Name is derived from Persian','The name sitar is derived from the Persian words \'seh\' and \'tar, which mean \'three strings\' when translated to English. '),(255,385,'Up to 21 strings','Sitars today can have as many as 21 strings. It is played by plucking. The plectrum used to play the sitar is called the mezrab. '),(256,385,'Multiple playing styles','Sitar playing styles can vary as well with different models including student, beginner, semi-professional, professional and master. '),(257,385,'Sitar for collections','Some of the most valuable sitars today are collectible and made by masters including Rikhi Ram from Delhi and Hiren Roy from Kolkata. '),(258,385,'Ravi Shankar','One of the most famous sitar players Ravi Shankar was a follower of the Maihar gharana which used seven playable strings. '),(259,385,'Popular in modern music in the 1906s','The sitar became popular in modern music for a period in the 1960s when it was used in music by The Rolling Stones, The Doors and The Beatles. '),(260,385,'George Harrison played sitar','George Harrison, a member of the iconic group The Beatles, learned to play the sitar from Ravi Shankar. He then used the instrument in several of The Beatles songs.'),(261,386,'In Italy is called little drum','In Italy the snare drum is referred to as a tamburo piccolo, which means little drum.'),(262,386,'Added to orchestra two centuries ago','The snare drum became a member of the orchestra approximately two centuries ago.'),(263,386,'Different types','There are different types of snare drums including the marching snare, drum kit snare, piccolo snare, tabor, tarol  and the Caixa Malacaheta. '),(264,386,'Produces a definite pitch','The snare drum produces a definite pitch. Prior to the 1900s a definite pitch was avoided in the snare drum but today it is required in some cases. '),(265,386,'Mammy-daddy beats','When playing the snare drum the stick is sometimes allowed to bounce back and hit the head again - a technique referred to as mammy-daddy beats.'),(266,386,'Snares inside the drum','Inside of the drum there are between 8 and 18 snares, which are made of plastic, metal, nylon or silk, stretched across the snare head. This is also the reason it is called the \'snare drum\'. '),(267,387,'Also reffered to as wadaiko','Taiko are also referred to as wadaiko and when an ensemble is playing taiko they are referred to as kumi-daiko. '),(268,387,'Elaborated build process','It can take several years to build a taiko depending on the craftsman\'s process and material used. '),(269,387,'Popular in Japanese theatre','Taiko are popular in Japanese theatre as they can be used to produce dramatic tension, rhythmic patterns and set the tone for the performance. '),(270,387,'Used by military','When taiko were used in warfare they used specific drum calls to communicate specific orders. The sets and beats would tell the troops whether to advance or retreat. '),(271,387,'Kumi-daiko created in 1951','Kumi-daiko, the ensemble playing of taiko was created in 1951 by Daihachi Oguchi, a trained jazz musician. '),(272,387,'Traditionaly made from zelkova tree','Traditional taiko were made from dried trunks of the zelkova tree. It took several years to dry the wood properly so that it would not split.'),(273,388,'Used by dancers','Tambourines in many cultures are used by dancers in religious ceremonies or in entertainment. '),(274,388,'The Nutcracker Suite','Tchaikovsky included the tambourine in the famous ballet The Nutcracker Suite. '),(275,388,'Can be used with drumsticks','If using a tool to strike the tambourine the musician can use drumsticks, felt beaters or even triangle beaters, as well as other tools they choose to create the desired sound. '),(276,388,'20 jingle pairs','A traditional tambourine used for an orchestra has 20 jingle pairs. This number can be smaller for ensemble tambourines and orchestras. '),(277,388,'Do not require tuning','Tambourines do not usually require tuning as the sound of striking the instrument is overshadowed by the jingles. '),(278,388,'Drum and rattle','The tambourine is considered a drum when it is struck and it is considered a rattle when it is shaken by the musician. '),(279,391,'Sounds like a bell','The triangle sounds like a bell when struck with the beater. '),(280,391,'Triangle concerto','Franz Liszt was the first to give the triangle a prominent piece in a composition - in Piano Concerto No. 1. The triangle had a solo in the third movement. The concerto became known as the \'triangle concerto\' because of this. '),(281,391,'Made of steel','The first triangles were made of solid iron. They were then changed to be made of steel rod and eventually steel or brass. '),(282,391,'Used by classic maestros','Wolfgang Amadeus Mozart, Ludwig Van Beethoven and Joseph Haydn all used the triangle in compositions. '),(283,391,'Can replace drum for beat','The triangle is often used in Cajun style music if no drum is available to give the beat. '),(284,391,'Beater influences the sound','The size and weight of the beater is important as it directly influences the sound produced when it is struck against the triangle. '),(285,394,'Ukulele in Japan','The ukulele was introduced in Japan in 1929 and it grew in popularity there along with other forms of Western music. '),(286,394,'Iconic jazz instrument','During the jazz age in the United States the ukulele was an iconic instrument. '),(287,394,'Usualy have four strings','Ukuleles most commonly have four strings but some are paired and therefore an instrument may have as many as eight strings.'),(288,394,'Different sizes','Common ukuleles include the baritone, tenor, soprano and concert ukulele sizes.  Less common ukulele sizes include the small sopranino ukulele which is also referred to as the pocket uke, bambino or piccolo. '),(289,394,'Taropatch ukulele','A six or eight string ukulele is often referred to as a taropatch ukulele. '),(290,394,'George Harrison was ukulele fan','George Harrison of the iconic band The Beatles was a ukulele fan and had a collection of hundreds. He often gave them away to friends. '),(291,395,'Model 145 created in 1927','The original vibraphone with its current features was called the \'Model 145\', and any changes since this model was created in 1927 have been mainly in the instruments size and weight. '),(292,395,'Big band music','The jazz musician Benny Goodman added the vibraphone to his own music and it became a huge part of big band music. '),(293,395,'Louis Armstrong loved vibraphone','Louis Armstrong was a fan of the sound created by the vibraphone. In 1931 Louis Armstrong and Lionel Hampton (considered to be a vibraphone genius) recorded together. '),(294,395,'Motor-driven bufferfly valve','The vibraphone resembles the xylophone but each bar is paired with a resonator tube that has a motor-driven butterfly valve at its upper end. The valves are mounted on a common shaft, which produces a tremolo or vibrato effect while spinning.'),(295,395,'Mallet with rubber ball','The vibraphone mallet is usually manufactured with a rubber ball at the end (the part that strikes the aluminum bars). '),(296,395,'Different mallet holding techniques','There are different techniques for holding the mallet when playing the vibraphone. Some hold the mallet between the index finger and thumb, and some players have many different mallets to enable them to create different sounds. '),(297,399,'African xylophones','The African xylophone is referred to as the balafon; in Mozambique is named mbilia; in Ghana, Burkina Faso, Mali and West Africa is gyil; in western Zambia the xylophone is referred to as the silimba. '),(298,399,'Used before vibraphone on jazz','Jazz musicians often used the xylophone in their bands until the vibraphone became more popular in this style of music in the 1940s. '),(299,399,'Mallets from diverse materials','Mallets used for the xylophone can be made of a variety of material including wood, metal or rubber. '),(300,399,'Xylophone keyboard','Xylophones used in concert halls often have 42 to 48 wooden bars and are similar in appearance to a keyboard. '),(301,399,'Bars are able to vibrate','The bars of a xylophone are strung together with string or cord and held in place while being able to vibrate when struck with the mallet to produce sound. '),(302,399,'Some have resonator tubes','On some models, under each bar of the xylophone is a resonator tube that amplifies the sound. '),(303,399,'Sound depends on used wood','The type of wood used to create the bars of the xylophone can result in different sounds. Hardwood is the most common but bamboo has also been used. ');
/*!40000 ALTER TABLE `atlasobject_facts` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `atlasobject_links`
--

DROP TABLE IF EXISTS `atlasobject_links`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `atlasobject_links` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `atlasobject_id` int(11) NOT NULL,
  `url` varchar(128) NOT NULL,
  `name` varchar(45) NOT NULL,
  `description` tinytext NOT NULL,
  `iconPath` varchar(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uq_link_url` (`url`,`atlasobject_id`),
  KEY `idx_link_atlasobject_id` (`atlasobject_id`),
  CONSTRAINT `fk_link_atlasobject_id` FOREIGN KEY (`atlasobject_id`) REFERENCES `atlasobject` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=814 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `atlasobject_links`
--

LOCK TABLES `atlasobject_links` WRITE;
/*!40000 ALTER TABLE `atlasobject_links` DISABLE KEYS */;
INSERT INTO `atlasobject_links` VALUES (643,321,'https://en.wikipedia.org/wiki/Accordion','Wikipedia','Wikipedia article about accordion.','links/wikipedia.png'),(644,321,'http://www.softschools.com/facts/music_instruments/accordion_facts/3037/','Soft Schools','Soft Schools facts about accordion.','links/softschools.png'),(645,322,'https://en.wikipedia.org/wiki/Alphorn','Wikipedia','Wikipedia article about alphorn.','links/wikipedia.png'),(646,323,'https://en.wikipedia.org/wiki/Bagpipes','Wikipedia','Wikipedia article about bagpipes.','links/wikipedia.png'),(647,323,'http://www.softschools.com/facts/music_instruments/bagpipe_facts/3038/','Soft Schools','Soft Schools facts about bagpipes.','links/softschools.png'),(648,324,'https://en.wikipedia.org/wiki/Balalaika','Wikipedia','Wikipedia article about balalaika.','links/wikipedia.png'),(649,325,'https://en.wikipedia.org/wiki/Bandoneon','Wikipedia','Wikipedia article about bandoneon.','links/wikipedia.png'),(650,326,'https://en.wikipedia.org/wiki/Bandura','Wikipedia','Wikipedia article about bandura.','links/wikipedia.png'),(651,327,'https://en.wikipedia.org/wiki/Banjo','Wikipedia','Wikipedia article about banjo','links/wikipedia.png'),(652,327,'http://www.softschools.com/facts/music_instruments/banjo_facts/3039/','Soft Schools','Soft Schools facts about banjo','links/softschools.png'),(653,328,'https://en.wikipedia.org/wiki/Bansuri','Wikipedia','Wikipedia article about bansuri.','links/wikipedia.png'),(655,329,'https://en.wikipedia.org/wiki/Bass_clarinet','Wikipedia','Wikipedia article about bass clarinet.','links/wikipedia.png'),(657,330,'https://en.wikipedia.org/wiki/Bass_drum','Wikipedia','Wikipedia article about bass drum.','links/wikipedia.png'),(658,330,'http://www.softschools.com/facts/music_instruments/bass_drum_facts/3004/','Soft Schools','Soft Schools facts about bass drum.','links/softschools.png'),(659,331,'https://en.wikipedia.org/wiki/Bass_flute','Wikipedia','Wikipedia article about bass flute.','links/wikipedia.png'),(661,332,'https://en.wikipedia.org/wiki/Bass_guitar','Wikipedia','Wikipedia article about bass guitar.','links/wikipedia.png'),(663,333,'https://en.wikipedia.org/wiki/Bucium','Wikipedia','Wikipedia article about bucium.','links/wikipedia.png'),(665,334,'https://en.wikipedia.org/wiki/Bugle','Wikipedia','Wikipedia article about bugle.','links/wikipedia.png'),(666,334,'http://www.softschools.com/facts/music_instruments/bugle_facts/3041/','Soft Schools','Soft Schools facts about bugle.','links/softschools.png'),(667,335,'https://en.wikipedia.org/wiki/Castanets','Wikipedia','Wikipedia article about castanets.','links/wikipedia.png'),(668,335,'http://www.softschools.com/facts/music_instruments/castanets_facts/3000/','Soft Schools','Soft Schools facts about castanets.','links/softschools.png'),(669,336,'https://en.wikipedia.org/wiki/Celesta','Wikipedia','Wikipedia article about celesta.','links/wikipedia.png'),(670,336,'http://www.softschools.com/facts/music_instruments/celesta_facts/3006/','Soft Schools','Soft Schools facts about celesta.','links/softschools.png'),(671,337,'https://en.wikipedia.org/wiki/Cello','Wikipedia','Wikipedia article about cello.','links/wikipedia.png'),(673,338,'https://en.wikipedia.org/wiki/Celtic_harp','Wikipedia','Wikipedia article about celtic harp.','links/wikipedia.png'),(675,339,'https://en.wikipedia.org/wiki/Cimbalom','Wikipedia','Wikipedia article about cimbalom.','links/wikipedia.png'),(677,340,'https://en.wikipedia.org/wiki/Clapstick','Wikipedia','Wikipedia article about clapstick.','links/wikipedia.png'),(679,341,'https://en.wikipedia.org/wiki/Clarinet','Wikipedia','Wikipedia article about clarinet.','links/wikipedia.png'),(680,341,'http://www.softschools.com/facts/music_instruments/clarinet_facts/3042/','Soft Schools','Soft Schools facts about clarinet.','links/softschools.png'),(681,342,'https://en.wikipedia.org/wiki/Cobza','Wikipedia','Wikipedia article about cobza.','links/wikipedia.png'),(683,343,'https://en.wikipedia.org/wiki/Concertina','Wikipedia','Wikipedia article about concertina.','links/wikipedia.png'),(685,344,'https://en.wikipedia.org/wiki/Cuatro_(instrument)','Wikipedia','Wikipedia article about cuatro.','links/wikipedia.png'),(687,345,'https://en.wikipedia.org/wiki/C%C3%BCmb%C3%BC%C5%9F','Wikipedia','Wikipedia article about cümbüş.','links/wikipedia.png'),(689,346,'https://en.wikipedia.org/wiki/Daf','Wikipedia','Wikipedia article about daf.','links/wikipedia.png'),(691,347,'https://en.wikipedia.org/wiki/%C4%90%C3%A0n_b%E1%BA%A7u','Wikipedia','Wikipedia article about Đàn bầu.','links/wikipedia.png'),(693,348,'https://en.wikipedia.org/wiki/Dhol','Wikipedia','Wikipedia article about dhol.','links/wikipedia.png'),(695,349,'https://en.wikipedia.org/wiki/Didgeridoo','Wikipedia','Wikipedia article about didgeridoo.','links/wikipedia.png'),(697,350,'https://en.wikipedia.org/wiki/Double_bass','Wikipedia','Wikipedia article about double bass.','links/wikipedia.png'),(699,351,'https://en.wikipedia.org/wiki/Drum_kit','Wikipedia','Wikipedia article about drum kit.','links/wikipedia.png'),(700,351,'http://www.softschools.com/facts/music_instruments/drum_kit_facts/3008/','Soft Schools','Soft Schools facts about drum kit.','links/softschools.png'),(701,352,'https://en.wikipedia.org/wiki/Duduk','Wikipedia','Wikipedia article about duduk.','links/wikipedia.png'),(703,353,'https://en.wikipedia.org/wiki/Dulcitone','Wikipedia','Wikipedia article about dulcitone.','links/wikipedia.png'),(705,354,'https://en.wikipedia.org/wiki/Esraj','Wikipedia','Wikipedia article about esraj.','links/wikipedia.png'),(707,355,'https://en.wikipedia.org/wiki/Flute','Wikipedia','Wikipedia article about flute.','links/wikipedia.png'),(711,357,'https://en.wikipedia.org/wiki/Glockenspiel','Wikipedia','Wikipedia article about glockenspiel.','links/wikipedia.png'),(712,357,'http://www.softschools.com/facts/music_instruments/glockenspiel_facts/3001/','Soft Schools','Soft Scools facts about glockenspiel.','links/softschools.png'),(713,358,'https://en.wikipedia.org/wiki/Goblet_drum','Wikipedia','Wikipedia article about goblet drum.','links/wikipedia.png'),(715,359,'https://en.wikipedia.org/wiki/Guitar','Wikipedia','Wikipedia article about guitar.','links/wikipedia.png'),(716,359,'http://www.softschools.com/facts/music_instruments/guitar_facts/3048/','Soft Schools','Soft Schools facts about guitar.','links/softschools.png'),(717,360,'https://en.wikipedia.org/wiki/Guqin','Wikipedia','Wikipedia article about guqin.','links/wikipedia.png'),(719,361,'https://en.wikipedia.org/wiki/Gusli','Wikipedia','Wikipedia article about gusli.','links/wikipedia.png'),(721,362,'https://en.wikipedia.org/wiki/Guzheng','Wikipedia','Wikipedia article about guzheng.','links/wikipedia.png'),(723,363,'https://en.wikipedia.org/wiki/Hammered_dulcimer','Wikipedia','Wikipedia article about hammered dulcimer.','links/wikipedia.png'),(725,364,'https://en.wikipedia.org/wiki/Harmonica','Wikipedia','Wikipedia article about harmonica.','links/wikipedia.png'),(729,366,'https://en.wikipedia.org/wiki/Harp','Wikipedia','Wikipedia article about harp.','links/wikipedia.png'),(730,366,'http://www.softschools.com/facts/music_instruments/harp_facts/3050/','Soft Schools','Soft Schools facts about harp.','links/softschools.png'),(731,367,'https://en.wikipedia.org/wiki/Horagai','Wikipedia','Wikipedia article about horagai.','links/wikipedia.png'),(733,368,'https://en.wikipedia.org/wiki/Mbira','Wikipedia','Wikipedia article about kalimba.','links/wikipedia.png'),(735,369,'https://en.wikipedia.org/wiki/Koto_(instrument)','Wikipedia','Wikipedia article about koto.','links/wikipedia.png'),(737,370,'https://en.wikipedia.org/wiki/Mandolin','Wikipedia','Wikipedia article about mandolin.','links/wikipedia.png'),(738,370,'http://www.softschools.com/facts/music_instruments/mandolin_facts/3055/','Soft Schools','Soft Schools facts about mandolin.','links/softschools.png'),(739,371,'https://en.wikipedia.org/wiki/Marimba','Wikipedia','Wikipedia article about marimba.','links/wikipedia.png'),(740,371,'http://www.softschools.com/facts/music_instruments/marimba_facts/3010/','Soft Schools','Soft Schools facts about marimba.','links/softschools.png'),(741,372,'https://en.wikipedia.org/wiki/Melodica','Wikipedia','Wikipedia article about melodica.','links/wikipedia.png'),(743,373,'https://en.wikipedia.org/wiki/Mridangam','Wikipedia','Wikipedia article about mridangam','links/wikipedia.png'),(745,374,'https://en.wikipedia.org/wiki/Mukkuri','Wikipedia','Wikipedia article about mukkuri.','links/wikipedia.png'),(747,375,'https://en.wikipedia.org/wiki/Oboe','Wikipedia','Wikipedia article about oboe.','links/wikipedia.png'),(749,376,'https://en.wikipedia.org/wiki/Ocarina','Wikipedia','Wikipedia article about ocarina.','links/wikipedia.png'),(751,377,'https://en.wikipedia.org/wiki/Pan_flute','Wikipedia','Wikipedia article about panpine.','links/wikipedia.png'),(753,378,'https://en.wikipedia.org/wiki/Piano','Wikipedia','Wikipedia article about piano.','links/wikipedia.png'),(755,379,'https://en.wikipedia.org/wiki/Piccolo','Wikipedia','Wikipedia article about picolo.','links/wikipedia.png'),(757,380,'https://en.wikipedia.org/wiki/Pipe_organ','Wikipedia','Wikipedia article about pipe organ.','links/wikipedia.png'),(759,381,'https://en.wikipedia.org/wiki/Pungi','Wikipedia','Wikipedia article about pungi.','links/wikipedia.png'),(761,382,'https://en.wikipedia.org/wiki/Saxophone','Wikipedia','Wikipedia article about saxophone.','links/wikipedia.png'),(763,383,'https://en.wikipedia.org/wiki/Shakuhachi','Wikipedia','Wikipedia article about shakuhachi.','links/wikipedia.png'),(765,384,'https://en.wikipedia.org/wiki/Shehnai','Wikipedia','Wikipedia article about shehnai.','links/wikipedia.png'),(767,385,'https://en.wikipedia.org/wiki/Sitar','Wikipedia','Wikipedia article about sitar.','links/wikipedia.png'),(769,386,'https://en.wikipedia.org/wiki/Snare_drum','Wikipedia','Wikipedia article about snare drum.','links/wikipedia.png'),(771,387,'https://en.wikipedia.org/wiki/Taiko','Wikipedia','Wikipedia article about taiko.','links/wikipedia.png'),(773,388,'https://en.wikipedia.org/wiki/Tambourine','Wikipedia','Wikipedia article about tambourine.','links/wikipedia.png'),(775,389,'https://en.wikipedia.org/wiki/Theatre_organ','Wikipedia','Wikipedia article about theatre organ.','links/wikipedia.png'),(777,390,'https://en.wikipedia.org/wiki/Tonkori','Wikipedia','Wikipedia article about tonkori.','links/wikipedia.png'),(779,391,'https://en.wikipedia.org/wiki/Triangle_(musical_instrument)','Wikipedia','Wikipedia article about triangle.','links/wikipedia.png'),(781,392,'https://en.wikipedia.org/wiki/Trumpet','Wikipedia','Wikipedia article about trumpet.','links/wikipedia.png'),(783,393,'https://en.wikipedia.org/wiki/Tuba','Wikipedia','Wikipedia article about tuba.','links/wikipedia.png'),(785,394,'https://en.wikipedia.org/wiki/Ukulele','Wikipedia','Wikipedia article about ukulele.','links/wikipedia.png'),(787,395,'https://en.wikipedia.org/wiki/Vibraphone','Wikipedia','Wikipedia article about vibraphone.','links/wikipedia.png'),(789,396,'https://en.wikipedia.org/wiki/Vienna_horn','Wikipedia','Wikipedia article about Vienna horn.','links/wikipedia.png'),(791,397,'https://en.wikipedia.org/wiki/Viola','Wikipedia','Wikipedia article about viola.','links/wikipedia.png'),(793,398,'https://en.wikipedia.org/wiki/Violin','Wikipedia','Wikipedia article about violin.','links/wikipedia.png'),(795,399,'https://en.wikipedia.org/wiki/Xylophone','Wikipedia','Wikipedia article about xylophone.','links/wikipedia.png'),(797,400,'https://en.wikipedia.org/wiki/Zither','Wikipedia','Wikipedia article about zither.','links/wikipedia.png'),(799,375,'http://www.softschools.com/facts/music_instruments/oboe_facts/3057/','Soft Schools','Soft Schools facts about oboe.','links/softschools.png'),(800,378,'http://www.softschools.com/facts/music_instruments/piano_facts/3058/','Soft Schools','Soft Schools facts about piano.','links/softschools.png'),(801,380,'http://www.softschools.com/facts/music_instruments/pipe_organ_facts/3059/','Soft Schools','Soft Schools facts about pipe organ.','links/softschools.png'),(806,385,'http://www.softschools.com/facts/music_instruments/sitar_facts/3062/','Soft Schools','Soft Schools facts about sitar.','links/softschools.png'),(807,386,'http://www.softschools.com/facts/music_instruments/snare_drum_facts/3011/','Soft Schools','Soft Schools facts about snare drum.','links/softschools.png'),(808,387,'http://www.softschools.com/facts/music_instruments/taiko_facts/3012/','Soft Schools','Soft Schools facts about taiko.','links/softschools.png'),(809,388,'http://www.softschools.com/facts/music_instruments/tambourine_facts/3014/','Soft Schools','Soft Schools facts about tambourine.','links/softschools.png'),(810,391,'http://www.softschools.com/facts/music_instruments/triangle_facts/3017/','Soft Schools','Soft Schools facts about triangle.','links/softschools.png'),(811,394,'http://www.softschools.com/facts/music_instruments/ukulele_facts/3065/','Soft Schools','Soft Schools facts about ukulele.','links/softschools.png'),(812,395,'http://www.softschools.com/facts/music_instruments/vibraphone_facts/3018/','Soft Schools','Soft Schools facts about vibraphone.','links/softschools.png'),(813,399,'http://www.softschools.com/facts/music_instruments/xylophone_facts/3003/','Soft Schools','Soft Schools facts about xylophone.','links/softschools.png');
/*!40000 ALTER TABLE `atlasobject_links` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `atlasobject_related`
--

DROP TABLE IF EXISTS `atlasobject_related`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `atlasobject_related` (
  `atlasobject_id` int(11) NOT NULL,
  `atlasobject_name` varchar(45) NOT NULL,
  PRIMARY KEY (`atlasobject_id`,`atlasobject_name`),
  KEY `ix_atlasobject_related_id` (`atlasobject_id`),
  KEY `ix_atlastobject_related_name` (`atlasobject_name`),
  CONSTRAINT `fk_atlasobject_related_id` FOREIGN KEY (`atlasobject_id`) REFERENCES `atlasobject` (`id`) ON DELETE CASCADE,
  CONSTRAINT `fk_atlastobject_related_name` FOREIGN KEY (`atlasobject_name`) REFERENCES `atlasobject` (`name`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `atlasobject_related`
--

LOCK TABLES `atlasobject_related` WRITE;
/*!40000 ALTER TABLE `atlasobject_related` DISABLE KEYS */;
INSERT INTO `atlasobject_related` VALUES (321,'bandoneon'),(321,'concertina'),(321,'harmonium'),(322,'bucium'),(322,'didgeridoo'),(323,'bugle'),(323,'horagai'),(324,'bandura'),(324,'cobza'),(324,'guitar'),(324,'mandolin'),(324,'sitar'),(324,'ukulele'),(325,'accordion'),(325,'concertina'),(325,'harmonium'),(326,'balalaika'),(326,'cobza'),(326,'gusli'),(326,'zither'),(327,'cobza'),(327,'cumbus'),(327,'mandolin'),(327,'sitar'),(327,'ukulele'),(328,'duduk'),(328,'pungi'),(328,'shakuhachi'),(328,'shehnai'),(329,'bass-flute'),(329,'clarinet'),(329,'flute'),(329,'oboe'),(329,'piccolo'),(330,'dhol'),(330,'drum-kit'),(330,'goblet-drum'),(330,'snare-drum'),(330,'taiko'),(331,'bass-clarinet'),(331,'clarinet'),(331,'flute'),(331,'oboe'),(332,'balalaika'),(332,'double-bass'),(332,'guitar'),(332,'mandolin'),(333,'alphorn'),(333,'bugle'),(333,'didgeridoo'),(334,'bagpipes'),(334,'horagai'),(334,'trumpet'),(335,'clapsticks'),(336,'dulcitone'),(336,'piano'),(337,'double-bass'),(337,'esraj'),(337,'viola'),(337,'violin'),(338,'harp'),(338,'kalimba'),(338,'mukkuri'),(339,'hammered-dulcimer'),(340,'castanets'),(341,'bass-clarinet'),(341,'bass-flute'),(341,'flute'),(341,'oboe'),(341,'piccolo'),(342,'balalaika'),(342,'guitar'),(342,'sitar'),(342,'ukulele'),(343,'accordion'),(343,'bandoneon'),(343,'harmonium'),(344,'banjo'),(344,'cobza'),(344,'guitar'),(344,'mandolin'),(345,'banjo'),(345,'cobza'),(345,'cuatro'),(345,'mandolin'),(346,'ghatam'),(346,'mridangam'),(346,'tambourine'),(347,'gusli'),(347,'guzheng'),(347,'koto'),(347,'zither'),(348,'bass-drum'),(348,'drum-kit'),(348,'snare-drum'),(348,'taiko'),(349,'alphorn'),(349,'bucium'),(350,'cello'),(350,'esraj'),(350,'viola'),(350,'violin'),(351,'bass-drum'),(351,'dhol'),(351,'goblet-drum'),(351,'snare-drum'),(351,'taiko'),(352,'bansuri'),(352,'pungi'),(352,'shakuhachi'),(352,'shehnai'),(353,'celesta'),(353,'piano'),(354,'cello'),(354,'double-bass'),(354,'viola'),(354,'violin'),(355,'bass-clarinet'),(355,'bass-flute'),(355,'clarinet'),(355,'oboe'),(355,'piccolo'),(356,'daf'),(356,'goblet-drum'),(356,'mridangam'),(357,'marimba'),(357,'triangle'),(357,'vibraphone'),(357,'xylophone'),(358,'daf'),(358,'ghatam'),(358,'mridangam'),(358,'tambourine'),(359,'bass-guitar'),(359,'cuatro'),(359,'mandolin'),(359,'ukulele'),(360,'dan-bau'),(360,'gusli'),(360,'guzheng'),(360,'koto'),(360,'zither'),(361,'guqin'),(361,'guzheng'),(361,'koto'),(361,'tonkori'),(361,'zither'),(362,'guqin'),(362,'gusli'),(362,'koto'),(362,'tonkori'),(362,'zither'),(363,'cimbalom'),(364,'accordion'),(364,'bandoneon'),(364,'concertina'),(364,'harmonium'),(365,'accordion'),(365,'bandoneon'),(365,'concertina'),(366,'celtic-harp'),(367,'ocarina'),(368,'mukkuri'),(369,'guqin'),(369,'gusli'),(369,'guzheng'),(369,'tonkori'),(370,'balalaika'),(370,'cuatro'),(370,'guitar'),(370,'ukulele'),(371,'glockenspiel'),(371,'vibraphone'),(371,'xylophone'),(372,'harmonica'),(373,'daf'),(373,'ghatam'),(373,'goblet-drum'),(374,'kalimba'),(375,'bass-clarinet'),(375,'clarinet'),(375,'flute'),(375,'piccolo'),(376,'horagai'),(377,'bansuri'),(377,'piccolo'),(378,'celesta'),(378,'dulcitone'),(379,'bass-clarinet'),(379,'clarinet'),(379,'flute'),(379,'oboe'),(380,'theatre-organ'),(381,'bansuri'),(381,'duduk'),(381,'shakuhachi'),(381,'shehnai'),(382,'trumpet'),(382,'tuba'),(382,'vienna-horn'),(383,'bansuri'),(383,'duduk'),(383,'pungi'),(383,'shehnai'),(384,'bansuri'),(384,'duduk'),(384,'pungi'),(384,'shakuhachi'),(385,'cobza'),(386,'bass-drum'),(386,'dhol'),(386,'drum-kit'),(386,'taiko'),(387,'bass-drum'),(387,'dhol'),(387,'drum-kit'),(387,'snare-drum'),(388,'daf'),(388,'ghatam'),(388,'mridangam'),(389,'pipe-organ'),(390,'guqin'),(390,'gusli'),(390,'guzheng'),(390,'koto'),(391,'glockenspiel'),(392,'saxophone'),(392,'tuba'),(392,'vienna-horn'),(393,'saxophone'),(393,'trumpet'),(393,'vienna-horn'),(394,'balalaika'),(394,'banjo'),(394,'guitar'),(394,'mandolin'),(395,'glockenspiel'),(395,'marimba'),(395,'xylophone'),(396,'saxophone'),(396,'trumpet'),(396,'tuba'),(397,'cello'),(397,'double-bass'),(397,'esraj'),(397,'violin'),(398,'cello'),(398,'double-bass'),(398,'esraj'),(398,'viola'),(399,'glockenspiel'),(399,'marimba'),(399,'vibraphone'),(400,'guqin'),(400,'gusli'),(400,'koto'),(400,'mandolin');
/*!40000 ALTER TABLE `atlasobject_related` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `atlasobject_spreading`
--

DROP TABLE IF EXISTS `atlasobject_spreading`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `atlasobject_spreading` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `atlasobject_id` int(11) NOT NULL,
  `name` varchar(45) NOT NULL,
  `area` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uq_region_area` (`atlasobject_id`,`name`,`area`),
  KEY `idx_region_atlasobject_id` (`atlasobject_id`),
  CONSTRAINT `fk_region_atlasobject_id` FOREIGN KEY (`atlasobject_id`) REFERENCES `atlasobject` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=85 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `atlasobject_spreading`
--

LOCK TABLES `atlasobject_spreading` WRITE;
/*!40000 ALTER TABLE `atlasobject_spreading` DISABLE KEYS */;
INSERT INTO `atlasobject_spreading` VALUES (11,322,'Switzerland',0),(76,323,'Africa',2),(77,323,'Asia',8),(75,323,'Europe',0),(12,324,'Russia',0),(13,325,'Argentina',0),(15,325,'Uruguay',0),(16,326,'Ukraine',0),(78,327,'United States',0),(17,328,'Afganistan',0),(18,328,'Bangladesh',0),(19,328,'Bhutan',0),(20,328,'India',0),(22,328,'Maldives',0),(23,328,'Nepal',0),(24,328,'Pakistan',0),(21,328,'Sri Lanka',0),(25,333,'Moldova',0),(26,333,'Romania',0),(29,335,'Italy',0),(30,335,'Portugal',0),(28,335,'Spain',0),(27,335,'Switzerland',0),(84,338,'Ireland',0),(32,339,'Croatia',0),(31,339,'Czechia',0),(33,339,'Hungary',0),(34,339,'Moldova',0),(35,339,'Poland',0),(36,339,'Romania',0),(37,339,'Slovakia',0),(38,339,'Ukraine',0),(39,340,'Australia',0),(40,342,'Hungary',0),(41,342,'Moldova',0),(42,342,'Romania',0),(43,345,'Turkey',0),(79,347,'Vietnam',0),(44,348,'Bangladesh',0),(45,348,'India',0),(46,348,'Pakistan',0),(47,349,'Australia',0),(48,352,'Armenia',0),(49,354,'India',0),(50,356,'India',0),(81,358,'Africa',2),(82,358,'Asia',6),(83,358,'Europe',4),(80,358,'Middle East',0),(51,360,'China',0),(52,361,'Russia',0),(53,362,'China',0),(54,365,'Afganistan',0),(55,365,'India',0),(56,365,'Nepal',0),(57,365,'Pakistan',0),(58,367,'Japan',0),(59,369,'Japan',0),(60,371,'Gatemala',0),(61,371,'Honduras',0),(62,371,'Mexico',0),(63,371,'Nicaragua',0),(64,373,'India',0),(65,374,'Japan',0),(66,381,'India',0),(67,381,'Pakistan',0),(68,383,'Japan',0),(69,384,'Bangladesh',0),(70,384,'India',0),(71,384,'Pakistan',0),(72,385,'India',0),(73,387,'Japan',0),(74,390,'Japan',0);
/*!40000 ALTER TABLE `atlasobject_spreading` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `audit`
--

DROP TABLE IF EXISTS `audit`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `audit` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `appId` int(11) NOT NULL,
  `deviceId` int(11) NOT NULL,
  `timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `ip` varchar(45) NOT NULL,
  `event` varchar(45) NOT NULL,
  `parameter1` varchar(80) DEFAULT NULL,
  `parameter2` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`,`appId`,`deviceId`),
  KEY `fk_audit_device1_idx` (`deviceId`),
  KEY `fk_audit_app1_idx` (`appId`),
  CONSTRAINT `fk_audit_app1` FOREIGN KEY (`appId`) REFERENCES `app` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_audit_device1` FOREIGN KEY (`deviceId`) REFERENCES `device` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `audit`
--

LOCK TABLES `audit` WRITE;
/*!40000 ALTER TABLE `audit` DISABLE KEYS */;
/*!40000 ALTER TABLE `audit` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `bird`
--

DROP TABLE IF EXISTS `bird`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `bird` (
  `id` int(11) NOT NULL,
  `wingspan_minimum` int(11) NOT NULL,
  `wingspan_maximum` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_bird_object_id` FOREIGN KEY (`id`) REFERENCES `atlasobject` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bird`
--

LOCK TABLES `bird` WRITE;
/*!40000 ALTER TABLE `bird` DISABLE KEYS */;
/*!40000 ALTER TABLE `bird` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `collection`
--

DROP TABLE IF EXISTS `collection`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `collection` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `display` varchar(45) DEFAULT NULL,
  `description` varchar(45) DEFAULT NULL,
  `iconPath` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `collection`
--

LOCK TABLES `collection` WRITE;
/*!40000 ALTER TABLE `collection` DISABLE KEYS */;
/*!40000 ALTER TABLE `collection` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `collection_atlasobject`
--

DROP TABLE IF EXISTS `collection_atlasobject`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `collection_atlasobject` (
  `collection_id` int(11) NOT NULL,
  `atlasobject_id` int(11) NOT NULL,
  PRIMARY KEY (`collection_id`,`atlasobject_id`),
  KEY `fk_collection_has_atlasobject_atlasobject1_idx` (`atlasobject_id`),
  KEY `fk_collection_has_atlasobject_collection1_idx` (`collection_id`),
  CONSTRAINT `fk_collection_has_atlasobject_atlasobject1` FOREIGN KEY (`atlasobject_id`) REFERENCES `atlasobject` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_collection_has_atlasobject_collection1` FOREIGN KEY (`collection_id`) REFERENCES `collection` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `collection_atlasobject`
--

LOCK TABLES `collection_atlasobject` WRITE;
/*!40000 ALTER TABLE `collection_atlasobject` DISABLE KEYS */;
/*!40000 ALTER TABLE `collection_atlasobject` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `crash_report`
--

DROP TABLE IF EXISTS `crash_report`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `crash_report` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `appId` int(11) NOT NULL,
  `deviceId` int(11) NOT NULL,
  `timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `ip` varchar(45) NOT NULL,
  `stackTrace` text NOT NULL,
  PRIMARY KEY (`id`,`appId`,`deviceId`),
  KEY `fk_crash_report_device1_idx` (`deviceId`),
  KEY `fk_crash_report_app1_idx` (`appId`),
  CONSTRAINT `fk_crash_report_app1` FOREIGN KEY (`appId`) REFERENCES `app` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_crash_report_device1` FOREIGN KEY (`deviceId`) REFERENCES `device` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `crash_report`
--

LOCK TABLES `crash_report` WRITE;
/*!40000 ALTER TABLE `crash_report` DISABLE KEYS */;
/*!40000 ALTER TABLE `crash_report` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `developer_message`
--

DROP TABLE IF EXISTS `developer_message`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `developer_message` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `appId` int(11) NOT NULL,
  `timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `ip` varchar(45) NOT NULL,
  `text` text NOT NULL,
  `developerName` varchar(45) DEFAULT NULL,
  `senderEmail` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`,`appId`),
  KEY `fk_message_app1_idx` (`appId`),
  CONSTRAINT `fk_message_app1` FOREIGN KEY (`appId`) REFERENCES `app` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `developer_message`
--

LOCK TABLES `developer_message` WRITE;
/*!40000 ALTER TABLE `developer_message` DISABLE KEYS */;
/*!40000 ALTER TABLE `developer_message` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `device`
--

DROP TABLE IF EXISTS `device`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `device` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `modelId` int(11) NOT NULL,
  `serial` varchar(45) NOT NULL,
  PRIMARY KEY (`id`,`modelId`),
  KEY `fk_device_model1_idx` (`modelId`),
  CONSTRAINT `fk_device_model1` FOREIGN KEY (`modelId`) REFERENCES `model` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `device`
--

LOCK TABLES `device` WRITE;
/*!40000 ALTER TABLE `device` DISABLE KEYS */;
/*!40000 ALTER TABLE `device` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dislike_reason`
--

DROP TABLE IF EXISTS `dislike_reason`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `dislike_reason` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `counterId` int(11) NOT NULL,
  `value` varchar(45) NOT NULL,
  PRIMARY KEY (`id`,`counterId`),
  KEY `fk_dislike_reason_like_counter_idx` (`counterId`),
  CONSTRAINT `fk_dislike_reason_like_counter` FOREIGN KEY (`counterId`) REFERENCES `like_counter` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dislike_reason`
--

LOCK TABLES `dislike_reason` WRITE;
/*!40000 ALTER TABLE `dislike_reason` DISABLE KEYS */;
/*!40000 ALTER TABLE `dislike_reason` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `i18ntext`
--

DROP TABLE IF EXISTS `i18ntext`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `i18ntext` (
  `text_hash` int(11) NOT NULL,
  `language` char(2) NOT NULL,
  `text` text NOT NULL,
  PRIMARY KEY (`text_hash`,`language`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `i18ntext`
--

LOCK TABLES `i18ntext` WRITE;
/*!40000 ALTER TABLE `i18ntext` DISABLE KEYS */;
/*!40000 ALTER TABLE `i18ntext` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `instrument`
--

DROP TABLE IF EXISTS `instrument`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `instrument` (
  `id` int(11) NOT NULL,
  `category` tinyint(4) NOT NULL,
  `sampleTitle` varchar(80) NOT NULL,
  `samplePath` varchar(45) NOT NULL,
  `waveformPath` varchar(45) NOT NULL,
  `date_value` bigint(20) NOT NULL,
  `date_mask` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_instrument_atlas_object1` FOREIGN KEY (`id`) REFERENCES `atlasobject` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `instrument`
--

LOCK TABLES `instrument` WRITE;
/*!40000 ALTER TABLE `instrument` DISABLE KEYS */;
INSERT INTO `instrument` VALUES (321,3,'Vittorio Monti - Csárdás','instruments/accordion/sample.mp3','instruments/accordion/waveform.png',1822,1),(322,2,'Swiss Alphorn Music','instruments/alphorn/sample.mp3','instruments/alphorn/waveform.png',14,3),(323,2,'Amazing Grace','instruments/bagpipes/sample.mp3','instruments/bagpipes/waveform.png',1000,65537),(324,1,'Oh God, Give me Strength','instruments/balalaika/sample.mp3','instruments/balalaika/waveform.png',17,771),(325,3,'Anibal Troilo - Ché Bandoneon','instruments/bandoneon/sample.mp3','instruments/bandoneon/waveform.png',1846,1),(326,1,'Bandura Solo','instruments/bandura/sample.mp3','instruments/bandura/waveform.png',1441,1),(327,1,'American Banjo Solo','instruments/banjo/sample.mp3','instruments/banjo/waveform.png',17,3),(328,2,'Indian Bansuri Solo','instruments/bansuri/sample.mp3','instruments/bansuri/waveform.png',100,1),(329,2,'Bach - Cello Suite No.1 in G','instruments/bass-clarinet/sample.mp3','instruments/bass-clarinet/waveform.png',18,515),(330,0,'The Basses Extravaganza','instruments/bass-drum/sample.mp3','instruments/bass-drum/waveform.png',14,3),(331,2,'Bass Flute Solo','instruments/bass-flute/sample.mp3','instruments/bass-flute/waveform.png',1910,1),(332,1,'Fast Slap Bass Guitar Solo','instruments/bass-guitar/sample.mp3','instruments/bass-guitar/waveform.png',193,2),(333,2,'Moldavian Bucium Tune','instruments/bucium/sample.mp3','instruments/bucium/waveform.png',100,65537),(334,2,'Bugle Call - The Last Post','instruments/bugle/sample.mp3','instruments/bugle/waveform.png',1758,1),(335,0,'Giménez - La boda de Luis Alonso','instruments/castanets/sample.mp3','instruments/castanets/waveform.png',1000,65537),(336,3,'Tchaikovsky - Dance of the Sugar Plum Fairy','instruments/celesta/sample.mp3','instruments/celesta/waveform.png',1886,1),(337,1,'Bach - Cello Suite No.1 in G','instruments/cello/sample.mp3','instruments/cello/waveform.png',16,515),(338,1,'Celtic Harp Solo','instruments/celtic-harp/sample.mp3','instruments/celtic-harp/waveform.png',15,3),(339,1,'Cimbalom Solo','instruments/cimbalom/sample.mp3','instruments/cimbalom/waveform.png',3500,65537),(340,0,'Australian Clapsticks','instruments/clapsticks/sample.mp3','instruments/clapsticks/waveform.png',600,1),(341,2,'Mozart - Clarinet Concerto in A Major','instruments/clarinet/sample.mp3','instruments/clarinet/waveform.png',17,771),(342,1,'Cobza Solo','instruments/cobza/sample.mp3','instruments/cobza/waveform.png',15,3),(343,3,'Motorcycle Polka','instruments/concertina/sample.mp3','instruments/concertina/waveform.png',1829,1),(344,1,'Venezuelan Cuatro Assembly','instruments/cuatro/sample.mp3','instruments/cuatro/waveform.png',15,3),(345,1,'Cümbüş Solo','instruments/cumbus/sample.mp3','instruments/cumbus/waveform.png',1930,1),(346,0,'Modern Daf Improvisation','instruments/daf/sample.mp3','instruments/daf/waveform.png',300,1),(347,1,'999 Đóa Hồng','instruments/dan-bau/sample.mp3','instruments/dan-bau/waveform.png',1770,1),(348,0,'Indian Dhol Solo','instruments/dhol/sample.mp3','instruments/dhol/waveform.png',15,3),(349,2,'Didgeridoo Solo','instruments/didgeridoo/sample.mp3','instruments/didgeridoo/waveform.png',600,1),(350,1,'Bottesini - Concerto for Double Bass No 2 in B Minor','instruments/double-bass/sample.mp3','instruments/double-bass/waveform.png',15,3),(351,0,'Caravan - Improvisation on Drums','instruments/drum-kit/sample.mp3','instruments/drum-kit/waveform.png',19,515),(352,2,'Armenian Duduk Solo','instruments/duduk/sample.mp3','instruments/duduk/waveform.png',100,65537),(353,3,'Bernstein - One Hand, One Heart','instruments/dulcitone/sample.mp3','instruments/dulcitone/waveform.png',1860,1),(354,1,'Gayatry Mantra','instruments/esraj/sample.mp3','instruments/esraj/waveform.png',18,259),(355,2,'Kuhlau - Fantasy for Flute Solo Op. 38 D major','instruments/flute/sample.mp3','instruments/flute/waveform.png',40000,65537),(356,0,'Indian Ghatam Solo','instruments/ghatam/sample.mp3','instruments/ghatam/waveform.png',1000,65537),(357,0,'Mozart - The Magic Flute','instruments/glockenspiel/sample.mp3','instruments/glockenspiel/waveform.png',17,771),(358,0,'Istanbul Street Musician','instruments/goblet-drum/sample.mp3','instruments/goblet-drum/waveform.png',21,65539),(359,1,'Spanish Guitar Solo','instruments/guitar/sample.mp3','instruments/guitar/waveform.png',12,3),(360,1,'Guqin Solo','instruments/guqin/sample.mp3','instruments/guqin/waveform.png',1000,65537),(361,1,'Lullaby by Shakhanov','instruments/gusli/sample.mp3','instruments/gusli/waveform.png',6,771),(362,1,'Guzheng Solo - Jasmine Flower','instruments/guzheng/sample.mp3','instruments/guzheng/waveform.png',500,65537),(363,1,'Leontovych - Carol of the Bells','instruments/hammered-dulcimer/sample.mp3','instruments/hammered-dulcimer/waveform.png',900,65537),(364,2,'Sonny Boy - Harmonica Solo','instruments/harmonica/sample.mp3','instruments/harmonica/waveform.png',19,259),(365,3,'Harmonium Sazina in Ludhiana Punjab','instruments/harmonium/sample.mp3','instruments/harmonium/waveform.png',1842,1),(366,1,'Tschaikowsky - Waltz of the Flowers','instruments/harp/sample.mp3','instruments/harp/waveform.png',3500,65537),(367,2,'Walking Inside Mountain','instruments/horagai/sample.mp3','instruments/horagai/waveform.png',1000,1),(368,1,'Kalimba Solo for Lotus','instruments/kalimba/sample.mp3','instruments/kalimba/waveform.png',7,3),(369,1,'Sakura Played on Koto','instruments/koto/sample.mp3','instruments/koto/waveform.png',7,3),(370,1,'Vivaldi - Mandolin Concerto in C Major','instruments/mandolin/sample.mp3','instruments/mandolin/waveform.png',17,3),(371,0,'Trevino - Catching Shadows','instruments/marimba/sample.mp3','instruments/marimba/waveform.png',17,3),(372,3,'Solo Melodica Improvisation','instruments/melodica/sample.mp3','instruments/melodica/waveform.png',19,3),(373,0,'Mridangam Solo in Misra Chaapu','instruments/mridangam/sample.mp3','instruments/mridangam/waveform.png',1000,65537),(374,1,'Ainu Mukkuri Solo','instruments/mukkuri/sample.mp3','instruments/mukkuri/waveform.png',12,3),(375,2,'Vivaldi - Sonata for Oboe and Continuo in C Minor','instruments/oboe/sample.mp3','instruments/oboe/waveform.png',17,515),(376,2,'The Hobbit - Misty Mountains Cold','instruments/ocarina/sample.mp3','instruments/ocarina/waveform.png',1200,65537),(377,2,'The Lonely Shepherd','instruments/panpipe/sample.mp3','instruments/panpipe/waveform.png',10000,65537),(378,3,'Beethoven - Piano Concert no. 5','instruments/piano/sample.mp3','instruments/piano/waveform.png',1709,1),(379,2,'Piccolo Solo from The Stars and Stripes Forever','instruments/piccolo/sample.mp3','instruments/piccolo/waveform.png',1854,1),(380,3,'Bach - Toccata and Fugue in D minor','instruments/pipe-organ/sample.mp3','instruments/pipe-organ/waveform.png',3,65539),(381,2,'Pungi Rhythm of Rajasthan','instruments/pungi/sample.mp3','instruments/pungi/waveform.png',16,3),(382,2,'Georgia On My Mind - Sax Solo','instruments/saxophone/sample.mp3','instruments/saxophone/waveform.png',1846,1),(383,2,'Shakuhachi Solo','instruments/shakuhachi/sample.mp3','instruments/shakuhachi/waveform.png',8,3),(384,2,'Raag Thumri Khamaj on Shehnai','instruments/shehnai/sample.mp3','instruments/shehnai/waveform.png',16,3),(385,1,'Pancham Se Gara','instruments/sitar/sample.mp3','instruments/sitar/waveform.png',16,3),(386,0,'Military Snare Drum Cadence','instruments/snare-drum/sample.mp3','instruments/snare-drum/waveform.png',13,3),(387,0,'Taiko Solo','instruments/taiko/sample.mp3','instruments/taiko/waveform.png',6,3),(388,0,'Tambourine Solo','instruments/tambourine/sample.mp3','instruments/tambourine/waveform.png',3500,65537),(389,3,'The Billboard March','instruments/theatre-organ/sample.mp3','instruments/theatre-organ/waveform.png',20,259),(390,1,'Ainu Tonkori Solo','instruments/tonkori/sample.mp3','instruments/tonkori/waveform.png',12,3),(391,0,'Triangle Solo','instruments/triangle/sample.mp3','instruments/triangle/waveform.png',16,3),(392,2,'Hummel - Trumpet Concerto 3-rd','instruments/trumpet/sample.mp3','instruments/trumpet/waveform.png',15,65539),(393,2,'Tuba Solo - Jabba The Hutt','instruments/tuba/sample.mp3','instruments/tuba/waveform.png',1835,1),(394,1,'Ukulele Solo','instruments/ukulele/sample.mp3','instruments/ukulele/waveform.png',19,3),(395,0,'Gary Burton - Vibraphone Solo','instruments/vibraphone/sample.mp3','instruments/vibraphone/waveform.png',1921,1),(396,2,'Jurassic Park Theme','instruments/vienna-horn/sample.mp3','instruments/vienna-horn/waveform.png',184,2),(397,1,'Stravinsky - Elegy for Viola Solo','instruments/viola/sample.mp3','instruments/viola/waveform.png',16,515),(398,1,'Porumbescu - Balada Op. 29','instruments/violin/sample.mp3','instruments/violin/waveform.png',16,259),(399,0,'Shostakovich - The Golden Age','instruments/xylophone/sample.mp3','instruments/xylophone/waveform.png',9,3),(400,1,'Otto Erbes - Romanze in A-Dur','instruments/zither/sample.mp3','instruments/zither/waveform.png',16,3);
/*!40000 ALTER TABLE `instrument` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `keyword`
--

DROP TABLE IF EXISTS `keyword`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `keyword` (
  `name` char(20) NOT NULL,
  PRIMARY KEY (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `keyword`
--

LOCK TABLES `keyword` WRITE;
/*!40000 ALTER TABLE `keyword` DISABLE KEYS */;
/*!40000 ALTER TABLE `keyword` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `like_counter`
--

DROP TABLE IF EXISTS `like_counter`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `like_counter` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `ip` varchar(45) NOT NULL,
  `value` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `like_counter`
--

LOCK TABLES `like_counter` WRITE;
/*!40000 ALTER TABLE `like_counter` DISABLE KEYS */;
/*!40000 ALTER TABLE `like_counter` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `model`
--

DROP TABLE IF EXISTS `model`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `model` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `manufacturer` varchar(45) NOT NULL,
  `model` varchar(45) NOT NULL,
  `version` varchar(45) NOT NULL,
  `apiLevel` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `model`
--

LOCK TABLES `model` WRITE;
/*!40000 ALTER TABLE `model` DISABLE KEYS */;
/*!40000 ALTER TABLE `model` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `no_ads_survey`
--

DROP TABLE IF EXISTS `no_ads_survey`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `no_ads_survey` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `deviceId` int(11) DEFAULT NULL,
  `timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `ip` varchar(45) NOT NULL,
  `agree` tinyint(1) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_no_ads_survey_device1_idx` (`deviceId`),
  CONSTRAINT `fk_no_ads_survey_device1` FOREIGN KEY (`deviceId`) REFERENCES `device` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `no_ads_survey`
--

LOCK TABLES `no_ads_survey` WRITE;
/*!40000 ALTER TABLE `no_ads_survey` DISABLE KEYS */;
/*!40000 ALTER TABLE `no_ads_survey` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `search_index`
--

DROP TABLE IF EXISTS `search_index`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `search_index` (
  `keyword_name` char(20) NOT NULL,
  `atlasobject_id` int(11) NOT NULL,
  `relevance` int(11) NOT NULL,
  PRIMARY KEY (`keyword_name`,`atlasobject_id`),
  KEY `fk_search_index_atlas_object1_idx` (`atlasobject_id`),
  CONSTRAINT `fk_search_index_atlas_object1` FOREIGN KEY (`atlasobject_id`) REFERENCES `atlasobject` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_search_index_keyword1` FOREIGN KEY (`keyword_name`) REFERENCES `keyword` (`name`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `search_index`
--

LOCK TABLES `search_index` WRITE;
/*!40000 ALTER TABLE `search_index` DISABLE KEYS */;
/*!40000 ALTER TABLE `search_index` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `emailAddress` varchar(45) NOT NULL,
  `password` varchar(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `emailAddress_UNIQUE` (`emailAddress`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'iulian@gnotis.ro','qq');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Final view structure for view `app_load`
--

/*!50001 DROP TABLE IF EXISTS `app_load`*/;
/*!50001 DROP VIEW IF EXISTS `app_load`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `app_load` AS select `audit`.`id` AS `id`,`audit`.`appId` AS `appId`,`audit`.`timestamp` AS `timestamp`,count(0) AS `value` from `audit` where (`audit`.`event` = 'APP_LOAD') group by year(`audit`.`timestamp`),month(`audit`.`timestamp`),dayofmonth(`audit`.`timestamp`) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-12-25 23:47:07
