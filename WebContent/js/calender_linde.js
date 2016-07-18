//
// JavaScript Calendar Component
// Author: Robert W. Husted  (robert.husted@iname.com)
// Date:   8/22/1999
// Modified Date: 11/30/1999
// Modified By:   Robert W. Husted
// Notes:  Added frameset support (changed reference for "newWin" to "top.newWin")
//         Also changed Spanish "March" from "Marcha" to "Marzo"
//         Fixed JavaScript Date Anomaly affecting days > 28
//
//
//
// Usage:  Add the following lines of code to your page to enable the Calendar
//         component.
//
//
//         // THIS LINE LOADS THE JS LIBRARY FOR THE CALENDAR COMPONENT
//
//         <SCRIPT LANGUAGE="JavaScript" SRC="calendar.js"></SCRIPT>
//
//
//
//         // THIS LINE IS USED IN CONJUNCTION WITH A FORM FIELD (myDateField) IN A FORM (myForm).
//         // Replace "myForm" and "myDateField" WITH THE NAME OF YOUR FORM AND INPUT FIELD RESPECTIVELY
//         // WINDOW OPTIONS SET THE WIDTH, HEIGHT, AND X/Y POSITION OF THE CALENDAR WINDOW
//         // WITH TITLEBAR ON, ALL OTHER OPTIONS (TOOLBARS, ETC) ARE DISABLED BY DEFAULT
//
//         <A HREF="javascript:doNothing()" onClick="setDateField(document.myForm.myDateField);top.newWin = window.open('calendar.html','cal','dependent=yes,width=210,height=230,screenX=200,screenY=300,titlebar=yes')">
//         <IMG SRC="calendar.gif" BORDER=0></A><font size=1>Popup Calendar</font>
//
//
//
// Required Files:
//
//         calendar.js   - contains all JavaScript functions to make the calendar work
//
//         calendar.html - frameset document (not required if you call the showCalendar()
//                         function.  However, calling showCalendar() directly causes
//                         the Java Virtual Machine (JVM) to start which slows down the
//                         loading of the calendar.)
//
//
// Files Generally Included:
//
//         calendar.gif  - image that looks like a little calendar
//
//         yourPage.html - page that contains a form and a date field which implements
//                         the calendar component
//



// BEGIN USER-EDITABLE SECTION -----------------------------------------------------



// SPECIFY DATE FORMAT RETURNED BY THIS CALENDAR
// (THIS IS ALSO THE DATE FORMAT RECOGNIZED BY THIS CALENDAR)

// DATE FORMAT OPTIONS:
//
// dd   = 1 or 2-digit Day
// DD   = 2-digit Day
// mm   = 1 or 2-digit Month
// MM   = 2-digit Month
// yy   = 2-digit Year
// YY   = 4-digit Year
// yyyy = 4-digit Year
// month   = Month name in lowercase letters
// Month   = Month name in initial caps
// MONTH   = Month name in captital letters
// mon     = 3-letter month abbreviation in lowercase letters
// Mon     = 3-letter month abbreviation in initial caps
// MON     = 3-letter month abbreviation in uppercase letters
// weekday = name of week in lowercase letters
// Weekday = name of week in initial caps
// WEEKDAY = name of week in uppercase letters
// wkdy    = 3-letter weekday abbreviation in lowercase letters
// Wkdy    = 3-letter weekday abbreviation in initial caps
// WKDY    = 3-letter weekday abbreviation in uppercase letters
//
// Examples:
//
// calDateFormat = "mm/dd/yy";
// calDateFormat = "Weekday, Month dd, yyyy";
// calDateFormat = "wkdy, mon dd, yyyy";
// calDateFormat = "DD.MM.YY";     // FORMAT UNSUPPORTED BY JAVASCRIPT -- REQUIRES CUSTOM PARSING
//

calDateFormat    = "mm/dd/yyyy";

var DateField;
var FlagField;
var checkType = 0; //

var NO_CHECK = 0;	// No check needed for date
var LT = 1; 		// Date must be less than current date
var EQ = 2;			// Date must equal to current date
var LE = 3;			// Date must be less than or equal to current date
var	GT = 4;			// Date must be greater than current date
var	GE = 5;			// Date must be greater than or equal to current date



// CALENDAR COLORS
topBackground    = "white";         // BG COLOR OF THE TOP FRAME
bottomBackground = "white";         // BG COLOR OF THE BOTTOM FRAME
tableBGColor     = "white";         // BG COLOR OF THE BOTTOM FRAME'S TABLE
cellColor        = "white";     // TABLE CELL BG COLOR OF THE DATE CELLS IN THE BOTTOM FRAME
headingCellColor = "white";         // TABLE CELL BG COLOR OF THE WEEKDAY ABBREVIATIONS
headingTextColor = "green";         // TEXT COLOR OF THE WEEKDAY ABBREVIATIONS
dateColor        = "black";          // TEXT COLOR OF THE LISTED DATES (1-28+)
focusColor       = "#ff0000";       // TEXT COLOR OF THE SELECTED DATE (OR CURRENT DATE)
hoverColor       = "darkred";       // TEXT COLOR OF A LINK WHEN YOU HOVER OVER IT
fontStyle        = "10pt Arial";           // TEXT STYLE FOR DATES
headingFontStyle = "bold 10pt Arial";      // TEXT STYLE FOR WEEKDAY ABBREVIATIONS

// FORMATTING PREFERENCES
bottomBorder  = false;        // TRUE/FALSE (WHETHER TO DISPLAY BOTTOM CALENDAR BORDER)
tableBorder   = 1;            // SIZE OF CALENDAR TABLE BORDER (BOTTOM FRAME) 0=none



// END USER-EDITABLE SECTION -------------------------------------------------------



// DETERMINE BROWSER BRAND
var isNav = false;
var isIE  = false;

// ASSUME IT'S EITHER NETSCAPE OR MSIE
if (navigator.appName == "Netscape") {
    isNav = true;
}
else {
    isIE = true;
}

// GET CURRENTLY SELECTED LANGUAGE
selectedLanguage = navigator.language;

// PRE-BUILD PORTIONS OF THE CALENDAR WHEN THIS JS LIBRARY LOADS INTO THE BROWSER
buildCalParts();



// CALENDAR FUNCTIONS BEGIN HERE ---------------------------------------------------

// SET THE INITIAL VALUE OF THE GLOBAL DATE FIELD
/*function setDateField(dateField) {

    // ASSIGN THE INCOMING FIELD OBJECT TO A GLOBAL VARIABLE
    calDateField = dateField;

    // GET THE VALUE OF THE INCOMING FIELD
    inDate = dateField.value;

    // SET calDate TO THE DATE IN THE INCOMING FIELD OR DEFAULT TO TODAY'S DATE
    setInitialDate();

    // THE CALENDAR FRAMESET DOCUMENTS ARE CREATED BY JAVASCRIPT FUNCTIONS
    calDocTop    = buildTopCalFrame();
    calDocBottom = buildBottomCalFrame();
}*/

// SET THE INITIAL VALUE OF THE GLOBAL DATE FIELD
function setDateField(dateField, flag) {

    // ASSIGN THE INCOMING FIELD OBJECT TO A GLOBAL VARIABLE
    calDateField = dateField;

	popCalFlag = flag;

    ///////////////////////////////////////////////////////////////////////////////////

		dt=new Date(dateField.value);
	//alert("passing date == "+dt)

	year=dt.getFullYear()


	//alert("final year == "+year)
	dt.setYear(year);

	// GET THE VALUE OF THE INCOMING FIELD
	inDate=(dt.getMonth()+1)+"/"+dt.getDate()+"/"+dt.getYear()

	// GET THE VALUE OF THE INCOMING FIELD
	//inDate = dateField.value;


	//////////////////////////////////////////////////////////////////////////////////

    // SET calDate TO THE DATE IN THE INCOMING FIELD OR DEFAULT TO TODAY'S DATE
    setInitialDate();

    // THE CALENDAR FRAMESET DOCUMENTS ARE CREATED BY JAVASCRIPT FUNCTIONS
    calDocTop    = buildTopCalFrame();
    calDocBottom = buildBottomCalFrame();
}


// SET THE INITIAL CALENDAR DATE TO TODAY OR TO THE EXISTING VALUE IN dateField
function setInitialDate() {

    // CREATE A NEW DATE OBJECT (WILL GENERALLY PARSE CORRECT DATE EXCEPT WHEN "." IS USED AS A DELIMITER)
    // (THIS ROUTINE DOES *NOT* CATCH ALL DATE FORMATS, IF YOU NEED TO PARSE A CUSTOM DATE FORMAT, DO IT HERE)
    calDate = new Date(inDate);

    // IF THE INCOMING DATE IS INVALID, USE THE CURRENT DATE
    if (isNaN(calDate)) {

        // ADD CUSTOM DATE PARSING HERE
        // IF IT FAILS, SIMPLY CREATE A NEW DATE OBJECT WHICH DEFAULTS TO THE CURRENT DATE
        calDate = new Date();
    }

    // KEEP TRACK OF THE CURRENT DAY VALUE
    calDay  = calDate.getDate();

    // SET DAY VALUE TO 1... TO AVOID JAVASCRIPT DATE CALCULATION ANOMALIES
    // (IF THE MONTH CHANGES TO FEB AND THE DAY IS 30, THE MONTH WOULD CHANGE TO MARCH
    //  AND THE DAY WOULD CHANGE TO 2.  SETTING THE DAY TO 1 WILL PREVENT THAT)
    calDate.setDate(1);
}


// POPUP A WINDOW WITH THE CALENDAR IN IT
function showCalendar(dateField) {

    // SET INITIAL VALUE OF THE DATE FIELD AND CREATE TOP AND BOTTOM FRAMES
    setDateField(dateField);

    // USE THE JAVASCRIPT-GENERATED DOCUMENTS (calDocTop, calDocBottom) IN THE FRAMESET
    calDocFrameset =
        "<HTML><HEAD><TITLE>JavaScript Calendar</TITLE></HEAD>\n" +
        "<FRAMESET ROWS='60,*' FRAMEBORDER='0'>\n" +
        "  <FRAME NAME='topCalFrame' SRC='javascript:parent.opener.calDocTop' SCROLLING='no'>\n" +
        "  <FRAME NAME='bottomCalFrame' SRC='javascript:parent.opener.calDocBottom' SCROLLING='no'>\n" +
        "</FRAMESET>\n";

    // DISPLAY THE CALENDAR IN A NEW POPUP WINDOW
    top.newWin = window.open("javascript:parent.opener.calDocFrameset", "calWin", winPrefs);
    top.newWin.focus();
}


// CREATE THE TOP CALENDAR FRAME
function buildTopCalFrame() {

    // CREATE THE TOP FRAME OF THE CALENDAR
    var calDoc =
        "<HTML>" +
        "<HEAD>" +
        "	<LINK rel=stylesheet href='scripts/rccfinal.css' type='text/css'> </HEAD>" +
        "	<script>function call(val) { if ( (val < 1900) || (val > 2100) ) { alert('Cannot select a year earlier to 1900 and later to 2100'); document.calControl.year.value="+calDate.getFullYear()+"; parent.opener.setYear() ;return;} else {	parent.opener.setYear();} } </script>" +
        "<BODY BGCOLOR='" + topBackground + "'>" +
        "<FORM NAME='calControl' onSubmit='return false;'>" +
        "<CENTER>" +
        "<TABLE align=center WIDTH='100%' CELLPADDING=0 CELLSPACING=1 BORDER=0>" +
        "<TR><TD>" +
        "<CENTER><img src='images/left.gif' border=0 onClick='parent.opener.setPreviousMonth()'>" +
        getMonthSelect() +"<img src='images/right.gif' border=0 onClick='parent.opener.setNextMonth()' >&nbsp;&nbsp;<img src='images/left.gif' border=0 onClick='parent.opener.setPreviousYear()'><INPUT class=small NAME='year' VALUE='" + calDate.getFullYear() + "'TYPE=TEXT SIZE=4 MAXLENGTH=4 onChange='javascript:call(this.value);'><img src='images/right.gif' border=0 onClick='javascript:parent.opener.setNextYear();'>" +
        "</CENTER>" +
        "</TD>" +
        "</TR>" +
		//"<tr><td align=center><INPUT TYPE=BUTTON NAME='today' VALUE='Today' onClick='parent.opener.setToday();' style ='font-family: Arial; font-variant: small-caps; font-size: 8pt; font-weight: bold'></td></tr>" +
        "</TABLE>" +
        "</CENTER>" +
        "</FORM>" +
        "</BODY>" +
        "</HTML>";

    return calDoc;
}


// CREATE THE BOTTOM CALENDAR FRAME
// (THE MONTHLY CALENDAR)
function buildBottomCalFrame() {

    // START CALENDAR DOCUMENT
    var calDoc = calendarBegin;

    // GET MONTH, AND YEAR FROM GLOBAL CALENDAR DATE
    month   = calDate.getMonth();
    year    = calDate.getFullYear();

    // GET GLOBALLY-TRACKED DAY VALUE (PREVENTS JAVASCRIPT DATE ANOMALIES)
    day     = calDay;

    var i   = 0;

    // DETERMINE THE NUMBER OF DAYS IN THE CURRENT MONTH
    var days = getDaysInMonth();

    // IF GLOBAL DAY VALUE IS > THAN DAYS IN MONTH, HIGHLIGHT LAST DAY IN MONTH
    if (day > days) {
        day = days;
    }

    // DETERMINE WHAT DAY OF THE WEEK THE CALENDAR STARTS ON
    var firstOfMonth = new Date (year, month, 1);

    // GET THE DAY OF THE WEEK THE FIRST DAY OF THE MONTH FALLS ON
    var startingPos  = firstOfMonth.getDay();
    days += startingPos;

    // KEEP TRACK OF THE COLUMNS, START A NEW ROW AFTER EVERY 7 COLUMNS
    var columnCount = 0;

    // MAKE BEGINNING NON-DATE CELLS BLANK
    for (i = 0; i < startingPos; i++) {

        calDoc += blankCell;
	columnCount++;
    }

    // SET VALUES FOR DAYS OF THE MONTH
    var currentDay = 0;
    var dayType    = "weekday";

    // DATE CELLS CONTAIN A NUMBER
    for (i = startingPos; i < days; i++) {

	var paddingChar = "&nbsp;";

        // ADJUST SPACING SO THAT ALL LINKS HAVE RELATIVELY EQUAL WIDTHS
        if (i-startingPos+1 < 10) {
            padding = "&nbsp;&nbsp;";
        }
        else {
            padding = "&nbsp;";
        }

        // GET THE DAY CURRENTLY BEING WRITTEN
        currentDay = i-startingPos+1;

        // SET THE TYPE OF DAY, THE focusDay GENERALLY APPEARS AS A DIFFERENT COLOR
        if (currentDay == day) {
            dayType = "focusDay";
        }
        else {
            dayType = "weekDay";
        }

        // ADD THE DAY TO THE CALENDAR STRING
        calDoc += "<TD width=15% align=center bgcolor='" + cellColor + "'>" +
                 "<a class='" + dayType + "' href='javascript:parent.opener.returnDate(" +
                 currentDay + ")'>" + padding + currentDay + paddingChar + "</a></TD>";
        columnCount++;

        // START A NEW ROW WHEN NECESSARY
        if (columnCount % 7 == 0) {
            calDoc += "</TR><TR>";
        }
    }

    // MAKE REMAINING NON-DATE CELLS BLANK
    for (i=days; i<42; i++)  {

        calDoc += blankCell;
	columnCount++;

        // START A NEW ROW WHEN NECESSARY
        if (columnCount % 7 == 0) {
            calDoc += "</TR>";
            if (i<41) {
                calDoc += "<TR>";
            }
        }
    }

    // FINISH THE NEW CALENDAR PAGE
    calDoc += calendarEnd;

    // RETURN THE COMPLETED CALENDAR PAGE
    return calDoc;
}


// WRITE THE MONTHLY CALENDAR TO THE BOTTOM CALENDAR FRAME
function writeCalendar() {

    // CREATE THE NEW CALENDAR FOR THE SELECTED MONTH & YEAR
    calDocBottom = buildBottomCalFrame();

    // WRITE THE NEW CALENDAR TO THE BOTTOM FRAME
    top.newWin.frames['bottomCalFrame'].document.open();
    top.newWin.frames['bottomCalFrame'].document.write(calDocBottom);
    top.newWin.frames['bottomCalFrame'].document.close();
}


// SET THE CALENDAR TO TODAY'S DATE AND DISPLAY THE NEW CALENDAR
function setToday() {

    // SET GLOBAL DATE TO TODAY'S DATE
    calDate = new Date();

    // SET DAY MONTH AND YEAR TO TODAY'S DATE

    var month = calDate.getMonth();
    var year  = calDate.getFullYear();

    // SET MONTH IN DROP-DOWN LIST
    top.newWin.frames['topCalFrame'].document.calControl.month.selectedIndex = month;

    // SET YEAR VALUE
    top.newWin.frames['topCalFrame'].document.calControl.year.value = year;

	var day   = calDate.getDate();
    calDate.setDate(day);
    // DISPLAY THE NEW CALENDAR
    writeCalendar();
}


// SET THE GLOBAL DATE TO THE NEWLY ENTERED YEAR AND REDRAW THE CALENDAR
function setYear() {

    // GET THE NEW YEAR VALUE
    var year  = top.newWin.frames['topCalFrame'].document.calControl.year.value;

    // IF IT'S A FOUR-DIGIT YEAR THEN CHANGE THE CALENDAR
    if (isFourDigitYear(year)) {
        calDate.setFullYear(year);
        writeCalendar();
    }
    else {
        // HIGHLIGHT THE YEAR IF THE YEAR IS NOT FOUR DIGITS IN LENGTH
        top.newWin.frames['topCalFrame'].document.calControl.year.focus();
        top.newWin.frames['topCalFrame'].document.calControl.year.select();
    }
}


// SET THE GLOBAL DATE TO THE SELECTED MONTH AND REDRAW THE CALENDAR
function setCurrentMonth() {

    // GET THE NEWLY SELECTED MONTH AND CHANGE THE CALENDAR ACCORDINGLY
    var month = top.newWin.frames['topCalFrame'].document.calControl.month.selectedIndex;

    calDate.setMonth(month);
    writeCalendar();
}


// SET THE GLOBAL DATE TO THE PREVIOUS YEAR AND REDRAW THE CALENDAR
function setPreviousYear() {

    var year  = top.newWin.frames['topCalFrame'].document.calControl.year.value;

    if (isFourDigitYear(year) && year > 1000) {
        year--;
        calDate.setFullYear(year);
        top.newWin.frames['topCalFrame'].document.calControl.year.value = year;
        writeCalendar();
    }
}


// SET THE GLOBAL DATE TO THE PREVIOUS MONTH AND REDRAW THE CALENDAR
function setPreviousMonth() {

    var year  = top.newWin.frames['topCalFrame'].document.calControl.year.value;
    if (isFourDigitYear(year)) {
        var month = top.newWin.frames['topCalFrame'].document.calControl.month.selectedIndex;

        // IF MONTH IS JANUARY, SET MONTH TO DECEMBER AND DECREMENT THE YEAR
        if (month == 0) {
            month = 11;
            if (year > 1000) {
                year--;
                calDate.setFullYear(year);
                top.newWin.frames['topCalFrame'].document.calControl.year.value = year;
            }
        }
        else {
            month--;
        }
        calDate.setMonth(month);
        top.newWin.frames['topCalFrame'].document.calControl.month.selectedIndex = month;
        writeCalendar();
    }
}


// SET THE GLOBAL DATE TO THE NEXT MONTH AND REDRAW THE CALENDAR
function setNextMonth() {

    var year = top.newWin.frames['topCalFrame'].document.calControl.year.value;

    if (isFourDigitYear(year)) {
        var month = top.newWin.frames['topCalFrame'].document.calControl.month.selectedIndex;

        // IF MONTH IS DECEMBER, SET MONTH TO JANUARY AND INCREMENT THE YEAR
        if (month == 11) {
            month = 0;
            year++;
            calDate.setFullYear(year);
            top.newWin.frames['topCalFrame'].document.calControl.year.value = year;
        }
        else {
            month++;
        }
        calDate.setMonth(month);
        top.newWin.frames['topCalFrame'].document.calControl.month.selectedIndex = month;
        writeCalendar();
    }
}


// SET THE GLOBAL DATE TO THE NEXT YEAR AND REDRAW THE CALENDAR
function setNextYear() {

    var year  = top.newWin.frames['topCalFrame'].document.calControl.year.value;
    if (isFourDigitYear(year)) {
        year++;
        calDate.setFullYear(year);
        top.newWin.frames['topCalFrame'].document.calControl.year.value = year;
        writeCalendar();
    }
}


// GET NUMBER OF DAYS IN MONTH
function getDaysInMonth()  {

    var days;
    var month = calDate.getMonth()+1;
    var year  = calDate.getFullYear();

    // RETURN 31 DAYS
    if (month==1 || month==3 || month==5 || month==7 || month==8 ||
        month==10 || month==12)  {
        days=31;
    }
    // RETURN 30 DAYS
    else if (month==4 || month==6 || month==9 || month==11) {
        days=30;
    }
    // RETURN 29 DAYS
    else if (month==2)  {
        if (isLeapYear(year)) {
            days=29;
        }
        // RETURN 28 DAYS
        else {
            days=28;
        }
    }
    return (days);
}


// CHECK TO SEE IF YEAR IS A LEAP YEAR
function isLeapYear (Year) {

    if (((Year % 4)==0) && ((Year % 100)!=0) || ((Year % 400)==0)) {
        return (true);
    }
    else {
        return (false);
    }
}


// ENSURE THAT THE YEAR IS FOUR DIGITS IN LENGTH
function isFourDigitYear(year) {

    if (year.length != 4) {
        top.newWin.frames['topCalFrame'].document.calControl.year.value = calDate.getFullYear();
        top.newWin.frames['topCalFrame'].document.calControl.year.select();
        top.newWin.frames['topCalFrame'].document.calControl.year.focus();
    }
    else {
        return true;
    }
}


// BUILD THE MONTH SELECT LIST
function getMonthSelect() {

    // BROWSER LANGUAGE CHECK DONE PREVIOUSLY (navigator.language())
    // FIRST TWO CHARACTERS OF LANGUAGE STRING SPECIFIES THE LANGUAGE
    // (THE LAST THREE OPTIONAL CHARACTERS SPECIFY THE LANGUAGE SUBTYPE)
    // SET THE NAMES OF THE MONTH TO THE PROPER LANGUAGE (DEFAULT TO ENGLISH)

    // IF FRENCH
    if (selectedLanguage == "fr") {
        monthArray = new Array('Janvier', 'Février', 'Mars', 'Avril', 'Mai', 'Juin',
                               'Juillet', 'Aout', 'Septembre', 'Octobre', 'Novembre', 'Décembre');
    }
    // IF GERMAN
    else if (selectedLanguage == "de") {
        monthArray = new Array('Januar', 'Februar', 'März', 'April', 'Mai', 'Juni',
                               'Juli', 'August', 'September', 'Oktober', 'November', 'Dezember');
    }
    // IF SPANISH
    else if (selectedLanguage == "es") {
        monthArray = new Array('Enero', 'Febrero', 'Marzo', 'Abril', 'Mayo', 'Junio',
                               'Julio', 'Agosto', 'Septiembre', 'Octubre', 'Noviembre', 'Diciembre');
    }
    // DEFAULT TO ENGLISH
    else {
        monthArray = new Array('January', 'February', 'March', 'April', 'May', 'June',
                               'July', 'August', 'September', 'October', 'November', 'December');
    }

    // DETERMINE MONTH TO SET AS DEFAULT
    var activeMonth = calDate.getMonth();

    // START HTML SELECT LIST ELEMENT
    monthSelect = "<SELECT class='small' NAME='month' onChange='parent.opener.setCurrentMonth()'>";

    // LOOP THROUGH MONTH ARRAY
    for (i in monthArray) {

        // SHOW THE CORRECT MONTH IN THE SELECT LIST
        if (i == activeMonth) {
            monthSelect += "<OPTION SELECTED>" + monthArray[i] + "\n";
        }
        else {
            monthSelect += "<OPTION>" + monthArray[i] + "\n";
        }
    }
    monthSelect += "</SELECT>";

    // RETURN A STRING VALUE WHICH CONTAINS A SELECT LIST OF ALL 12 MONTHS
    return monthSelect;
}


// SET DAYS OF THE WEEK DEPENDING ON LANGUAGE
function createWeekdayList() {

    // IF FRENCH
    if (selectedLanguage == "fr") {
        weekdayList  = new Array('Dimanche', 'Lundi', 'Mardi', 'Mercredi', 'Jeudi', 'Vendredi', 'Samedi');
        weekdayArray = new Array('Di', 'Lu', 'Ma', 'Me', 'Je', 'Ve', 'Sa');
    }
    // IF GERMAN
    else if (selectedLanguage == "de") {
        weekdayList  = new Array('Sonntag', 'Montag', 'Dienstag', 'Mittwoch', 'Donnerstag', 'Freitag', 'Samstag');
        weekdayArray = new Array('So', 'Mo', 'Di', 'Mi', 'Do', 'Fr', 'Sa');
    }
    // IF SPANISH
    else if (selectedLanguage == "es") {
        weekdayList  = new Array('Domingo', 'Lunes', 'Martes', 'Miércoles', 'Jueves', 'Viernes', 'Sábado')
        weekdayArray = new Array('Do', 'Lu', 'Ma', 'Mi', 'Ju', 'Vi', 'Sa');
    }
    else {
        weekdayList  = new Array('Sunday', 'Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday');
        weekdayArray = new Array('Sun','Mon','Tue','Wed','Thu','Fri','Sat');
    }

    // START HTML TO HOLD WEEKDAY NAMES IN TABLE FORMAT
    var weekdays = "<TR BGCOLOR='" + headingCellColor + "'>";

    // LOOP THROUGH WEEKDAY ARRAY
    for (i in weekdayArray) {
		weekdays += "<TD class='heading' align=center>" + weekdayArray[i] + "</TD>";
    }
    weekdays += "</TR>";

    // RETURN TABLE ROW OF WEEKDAY ABBREVIATIONS TO DISPLAY ABOVE THE CALENDAR
    return weekdays;
}


// PRE-BUILD PORTIONS OF THE CALENDAR (FOR PERFORMANCE REASONS)
function buildCalParts() {

    // GENERATE WEEKDAY HEADERS FOR THE CALENDAR

    weekdays = createWeekdayList();

    // BUILD THE BLANK CELL ROWS
    blankCell = "<TD width=15% align=center bgcolor='" + cellColor + "'>&nbsp;&nbsp;&nbsp;</TD>";

    // BUILD THE TOP PORTION OF THE CALENDAR PAGE USING CSS TO CONTROL SOME DISPLAY ELEMENTS
    calendarBegin =
        "<HTML>" +
        "<HEAD>" +
        // STYLESHEET DEFINES APPEARANCE OF CALENDAR
        "<STYLE type='text/css'>" +
        "<!--" +
        "TD.heading { text-decoration: none; color:" + headingTextColor + "; font: " + headingFontStyle + "; }" +
        "A.focusDay:link { color: " + focusColor + "; text-decoration: none; font-family : Arial; font-size :10pt; font : bold; color : red; }" +
        "A.focusDay:hover { color: " + focusColor + "; text-decoration: none; font-family : Arial; font-size :10pt; font : bold; color : red; }" +
        "A.weekday:link { color: " + dateColor + "; text-decoration: none; font-family : Arial; font-size :10pt; color : black; }" +
        "A.weekday:hover { color: " + hoverColor + "; font-family : Arial; font-size :10pt; color : black; }" +
        "-->" +
        "</STYLE>" +
        "</HEAD>" +
        "<BODY BGCOLOR='" + bottomBackground + "' text=silver" +
        "<CENTER>";

        // NAVIGATOR NEEDS A TABLE CONTAINER TO DISPLAY THE TABLE OUTLINES PROPERLY
        if (isNav) {
            calendarBegin +=
                "<TABLE  WIDTH='100%' CELLPADDING=0 CELLSPACING=1 BORDER=" + tableBorder + " ALIGN=CENTER BGCOLOR='" + tableBGColor + "'><TR><TD>";
        }

        // BUILD WEEKDAY HEADINGS
        calendarBegin +=
            "<TABLE  WIDTH='100%' CELLPADDING=0 CELLSPACING=1 BORDER=" + tableBorder + " ALIGN=CENTER BGCOLOR='" + tableBGColor + "' style='border-collapse: collapse; mso-border-alt: solid windowtext .5pt;border-style: solid windowtext .5pt; border-width: medium'>" +
            weekdays +
            "<TR>";


    // BUILD THE BOTTOM PORTION OF THE CALENDAR PAGE
    calendarEnd = "";

        // WHETHER OR NOT TO DISPLAY A THICK LINE BELOW THE CALENDAR
        if (bottomBorder) {
            calendarEnd += "<TR></TR>";
        }

        // NAVIGATOR NEEDS A TABLE CONTAINER TO DISPLAY THE BORDERS PROPERLY
        if (isNav) {
            calendarEnd += "</TD></TR></TABLE>";
        }

        // END THE TABLE AND HTML DOCUMENT
        calendarEnd +=
            "</TABLE>" +
            "</CENTER>" +
            "</BODY>" +
            "</HTML>";
}


// REPLACE ALL INSTANCES OF find WITH replace
// inString: the string you want to convert
// find:     the value to search for
// replace:  the value to substitute
//
// usage:    jsReplace(inString, find, replace);
// example:  jsReplace("To be or not to be", "be", "ski");
//           result: "To ski or not to ski"
//
function jsReplace(inString, find, replace) {

    var outString = "";

    if (!inString) {
        return "";
    }

    // REPLACE ALL INSTANCES OF find WITH replace
    if (inString.indexOf(find) != -1) {
        // SEPARATE THE STRING INTO AN ARRAY OF STRINGS USING THE VALUE IN find
        t = inString.split(find);

        // JOIN ALL ELEMENTS OF THE ARRAY, SEPARATED BY THE VALUE IN replace
        return (t.join(replace));
    }
    else {
        return inString;
    }
}


// JAVASCRIPT FUNCTION -- DOES NOTHING (USED FOR THE HREF IN THE CALENDAR CALL)
function doNothing() {
}


// ENSURE THAT VALUE IS TWO DIGITS IN LENGTH
function makeTwoDigit(inValue) {

    var numVal = parseInt(inValue, 10);

    // VALUE IS LESS THAN TWO DIGITS IN LENGTH
    if (numVal < 10) {

        // ADD A LEADING ZERO TO THE VALUE AND RETURN IT
        return("0" + numVal);
    }
    else {
        return numVal;
    }
}


// SET FIELD VALUE TO THE DATE SELECTED AND CLOSE THE CALENDAR WINDOW
function returnDate(inDay)
{

    // inDay = THE DAY THE USER CLICKED ON
    calDate.setDate(inDay);

    // SET THE DATE RETURNED TO THE USER
    var day           = calDate.getDate();
    var month         = calDate.getMonth()+1;
    var year          = calDate.getFullYear();
    var monthString   = monthArray[calDate.getMonth()];
    var monthAbbrev   = monthString.substring(0,3);
    var weekday       = weekdayList[calDate.getDay()];
    var weekdayAbbrev = weekday.substring(0,3);

    outDate = calDateFormat;

    // RETURN TWO DIGIT DAY
    if (calDateFormat.indexOf("DD") != -1) {
        day = makeTwoDigit(day);
        outDate = jsReplace(outDate, "DD", day);
    }
    // RETURN ONE OR TWO DIGIT DAY
    else if (calDateFormat.indexOf("dd") != -1) {
        outDate = jsReplace(outDate, "dd", day);
    }

    // RETURN TWO DIGIT MONTH
    if (calDateFormat.indexOf("MM") != -1) {
        month = makeTwoDigit(month);
        outDate = jsReplace(outDate, "MM", month);
    }
    // RETURN ONE OR TWO DIGIT MONTH
    else if (calDateFormat.indexOf("mm") != -1) {
        outDate = jsReplace(outDate, "mm", month);
    }

	// RETURN FOUR-DIGIT YEAR
    if (calDateFormat.indexOf("yyyy") != -1) {
        outDate = jsReplace(outDate, "yyyy", year);
    }
    // RETURN TWO-DIGIT YEAR
    else if (calDateFormat.indexOf("yy") != -1) {
        var yearString = "" + year;
        var yearString = yearString.substring(2,4);
		outDate = jsReplace(outDate, "yy", yearString);
    }
    // RETURN FOUR-DIGIT YEAR
    else if (calDateFormat.indexOf("YY") != -1) {
        outDate = jsReplace(outDate, "YY", year);
    }

    // RETURN DAY OF MONTH (Initial Caps)
    if (calDateFormat.indexOf("Month") != -1) {
        outDate = jsReplace(outDate, "Month", monthString);
    }
    // RETURN DAY OF MONTH (lowercase letters)
    else if (calDateFormat.indexOf("month") != -1) {
        outDate = jsReplace(outDate, "month", monthString.toLowerCase());
    }
    // RETURN DAY OF MONTH (UPPERCASE LETTERS)
    else if (calDateFormat.indexOf("MONTH") != -1) {
        outDate = jsReplace(outDate, "MONTH", monthString.toUpperCase());
    }

    // RETURN DAY OF MONTH 3-DAY ABBREVIATION (Initial Caps)
    if (calDateFormat.indexOf("Mon") != -1) {
        outDate = jsReplace(outDate, "Mon", monthAbbrev);
    }
    // RETURN DAY OF MONTH 3-DAY ABBREVIATION (lowercase letters)
    else if (calDateFormat.indexOf("mon") != -1) {
        outDate = jsReplace(outDate, "mon", monthAbbrev.toLowerCase());
    }
    // RETURN DAY OF MONTH 3-DAY ABBREVIATION (UPPERCASE LETTERS)
    else if (calDateFormat.indexOf("MON") != -1) {
        outDate = jsReplace(outDate, "MON", monthAbbrev.toUpperCase());
    }

    // RETURN WEEKDAY (Initial Caps)
    if (calDateFormat.indexOf("Weekday") != -1) {
        outDate = jsReplace(outDate, "Weekday", weekday);
    }
    // RETURN WEEKDAY (lowercase letters)
    else if (calDateFormat.indexOf("weekday") != -1) {
        outDate = jsReplace(outDate, "weekday", weekday.toLowerCase());
    }
    // RETURN WEEKDAY (UPPERCASE LETTERS)
    else if (calDateFormat.indexOf("WEEKDAY") != -1) {
        outDate = jsReplace(outDate, "WEEKDAY", weekday.toUpperCase());
    }

    // RETURN WEEKDAY 3-DAY ABBREVIATION (Initial Caps)
    if (calDateFormat.indexOf("Wkdy") != -1) {
        outDate = jsReplace(outDate, "Wkdy", weekdayAbbrev);
    }
    // RETURN WEEKDAY 3-DAY ABBREVIATION (lowercase letters)
    else if (calDateFormat.indexOf("wkdy") != -1) {
        outDate = jsReplace(outDate, "wkdy", weekdayAbbrev.toLowerCase());
    }
    // RETURN WEEKDAY 3-DAY ABBREVIATION (UPPERCASE LETTERS)
    else if (calDateFormat.indexOf("WKDY") != -1) {
        outDate = jsReplace(outDate, "WKDY", weekdayAbbrev.toUpperCase());
    }

    // SET THE VALUE OF THE FIELD THAT WAS PASSED TO THE CALENDAR


	//////////////// ADDED CODE TO CHANGE YEAR FORMAT ////////////////
	
	tempDt=new Date(outDate);

    curDate = new Date();
    curDate.setDate(curDate.getDate() - 1);
	if((checkType == GE)&&(curDate > tempDt)){
		alert("Date cannot be earlier than today");
		FlagField = 0;
		callCalendar(DateField,FlagField,'calendar.html');
		return;
    }

	tempYear=tempDt.getYear();

	tempMonth=tempDt.getMonth()+1;
	tempDay=tempDt.getDate();

//	tempYear=tempYear-2000 ;

	if(tempDay<10)
	tempDay="0"+tempDay;

	if(tempMonth<10)
	tempMonth="0"+tempMonth;

//	calDateField.value=tempMonth+"/"+tempDay+"/"+tempYear ;
	calDateField.value=tempMonth+"/"+tempDay+"/"+year ;

	//////////////////////////////////////////////////////////////////////////////
	popCalFlag.value = "0";

    // GIVE FOCUS BACK TO THE DATE FIELD
    calDateField.focus();
    // CLOSE THE CALENDAR WINDOW
    top.newWin.close()
}


//This function is added to bring up the calendar if it is already there
function callCalendar(datef,Flag,htmlPath,check)
{
//alert("nag11"+Flag)
//Flag.value= '0';
//alert("nag22"+Flag.value);
	DateField = datef;
	FlagField = Flag;
	checkType = check;

//////////////////////////// CHAGED FOR YEAR FORMAT ///////////////////////////

//$$$$$$$$$$$$$$ PREVIOUS CODE $$$$$$$$$$$$$$$$$$$$
// setDateField(Date,Flag);
// $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$

// NEW CODE
	//alert("latest begin");
	//alert(" date field value : ")
		//alert(datef.value);
//	rscdt=eval(Date);

	dt=new Date(datef.value);

	year=dt.getYear();
	
	if(year<100)

		year=year+2000 ;

	dt.setYear(year);

	// datef.value=(dt.getMonth()+1)+"/"+dt.getDate()+"/"+dt.getYear()
//alert("datef="+datef);
//alert("Flag="+Flag);
	 setDateField(datef,Flag);

// END OF NEW CODE
//////////////////////////////////////////////////////////////////////////////////////////


	if (Flag.value=="0")
	{
//		alert("NAG");
		Flag.value="1";
		top.newWin = window.open(htmlPath,'cal','dependent=yes,width=250,height=230,screenX=500,screenY=500,titlebar=yes');
	}
	else
	{
		if (!top.newWin.closed)
		{
			top.newWin.focus();
		}
		else
		{
			top.newWin = window.open(htmlPath,'cal','dependent=yes,width=210,height=230,screenX=500,screenY=500,titlebar=yes');
		}
	}
}


function callCal(datef,datef1,Flag,htmlPath,check)
{

	DateField = datef;
	FlagField = Flag;
	checkType = check;

//////////////////////////// CHAGED FOR YEAR FORMAT ///////////////////////////

//$$$$$$$$$$$$$$ PREVIOUS CODE $$$$$$$$$$$$$$$$$$$$
// setDateField(Date,Flag);
// $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$

// NEW CODE
	//alert("latest begin");
	//alert(" date field value : ")
		//alert(datef.value);
//	rscdt=eval(Date);

	dt=new Date(datef.value);

	year=dt.getYear()
		//alert("year == "+year)

	if(year<100)

		year=year+2000 ;

	//alert("final year == "+year)
	dt.setYear(year);

	// datef.value=(dt.getMonth()+1)+"/"+dt.getDate()+"/"+dt.getYear()

	 setDateField(datef,Flag);

// END OF NEW CODE
//////////////////////////////////////////////////////////////////////////////////////////


	if (Flag.value=="0")
	{
		Flag.value="1";
		top.newWin = window.open(htmlPath,'cal','dependent=yes,width=250,height=230,screenX=500,screenY=500,titlebar=yes');
	}
	else
	{
		if (!top.newWin.closed)
		{
			top.newWin.focus();
		}
		else
		{
			top.newWin = window.open(htmlPath,'cal','dependent=yes,width=210,height=230,screenX=500,screenY=500,titlebar=yes');
		}
	}
}

	function checkFormat(date, check){
//Validate the format of the date
		if(!validateDate(date.value))
		{
			date.focus();
			return;
		}
		checkType = check;
		var tempDt = new Date(date.value);
		year = date.value.substr(6, 4);
		if(year < 100)
		{
			tempDt.setFullYear(tempDt.getFullYear()+100);
		}
		curDate = new Date();
	    curDate.setDate(curDate.getDate() - 1);
		if((checkType == GE)&&(curDate > tempDt)){
			alert("Date cannot be earlier than today");
			date.focus();
			return;
		}
	}
function strDate_compare(strDate1, strDate2) {

	var s1, mm1, dd1, yyyy1;
	var s2, mm2, dd2, yyyy2;

	s1 = strDate1.split("/");
	mm1 = Math.abs(s1[0]);
	dd1 = Math.abs(s1[1]);
	yyyy1 = Math.abs(s1[2]);

	s2 = strDate2.split("/");
	mm2 = Math.abs(s2[0]);
	dd2 = Math.abs(s2[1]);
	yyyy2 = Math.abs(s2[2]);

	if(yyyy1 > yyyy2)
		return 1;
	if(yyyy1 < yyyy2)
		return -1;
	if(mm1 >mm2)
		return 1;
	if(mm1 < mm2)
		return -1;
	if(dd1 > dd2)
		return 1;
	if(dd1 < dd2)
		return -1;
	return 0;
}	