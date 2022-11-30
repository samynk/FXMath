parser grammar MathDocumentParser;

@header {
    package dae.math.script;
}

options{tokenVocab=DocumentLexer;}

document: DOCUMENTSTART content DOCUMENTEND ;
content: (  rightColumn |(formatCommand) | paragraph | environment | (mathCommand) | inputCommand | (script) | documentFormula | documentCode | includescript |includegraphics | (picture) | picture3d | (enumeration) | (text) | mathTable )+ ;

mathTable: mtableStart colspec* mrow* mtableEnd;
colspec: LBRACE TEXT RBRACE;
mrow: BEGINMROW content (CDELIM content)+ ENDMROW;

mtableStart: BEGIN LBRACE MATHTABLE RBRACE;
mtableEnd: END LBRACE MATHTABLE RBRACE;

formatCommand: BACKSLASH TEXT LBRACE (text) RBRACE  ;
mathCommand: (VALUE|CAPTION|FORMULA) ID (PBACKSLASH ID)? TRANSPOSE? (FORMAT formatList=idList)? (FVALUES(fValues=idList)RPARENS)? (PVALUES(pValues=idList) (FORMAT formatID=ID)? RPARENS)? (CVALUES(cValues=idList) RPARENS)? PRBRACE ;
inputCommand: INPUT iID=ID (EQUALS qID=ID)? (CVALUES(cValues=idList)RPARENS)? (FORMAT fID=ID)?  PRBRACE;

idList: ID (COMMA ID)*;

script: BEGINSCRIPT SCRIPTBLOCK ;
documentFormula: BEGINFORMULA FORMULABLOCK;
documentCode: BEGINLISTING (LBRACE keys=TEXT RBRACE)? BEGINCODE   CODEBLOCK picturePart* ENDLISTING;

includescript: INCLUDESCRIPT LBRACE TEXT RBRACE;
includegraphics: INCLUDEGRAPHICS LBRACE keys=TEXT RBRACE;

picture: pictureStart (LBRACE keys=TEXT RBRACE)? (width|dim)? (pictureExtents)? xTicks? yTicks? pictureText? (pictureInclude)* picturePart* pictureEnd ;
pictureStart: BEGIN LBRACE PICTURE RBRACE ;
pictureEnd: END LBRACE PICTURE RBRACE ;

picture3d: pictureStart3D (LBRACE keys=TEXT RBRACE)? (width|dim)? pictureExtents? pictureScale? xTicks? yTicks? pictureText? (pictureInclude)* picturePart* pictureEnd3D ;
pictureStart3D: BEGIN LBRACE PICTURE3D RBRACE ;
pictureEnd3D: END LBRACE PICTURE3D RBRACE ;

rightColumn: rightColumnStart width? content rightColumnEnd;
rightColumnStart: BEGIN LBRACE RIGHTCOLUMN RBRACE;
rightColumnEnd: END LBRACE RIGHTCOLUMN RBRACE ;

paragraph: BEGINPARAGRAPH (LBRACE keys=TEXT RBRACE)?  content ENDPARAGRAPH;
environment: BEGIN LBRACE env=TEXT RBRACE (LBRACE keys=TEXT RBRACE)?  content? END LBRACE env=TEXT RBRACE;

pictureInclude: INCLUDE LBRACE TEXT RBRACE ;
pictureExclude: EXCLUDE LBRACE TEXT RBRACE ;
pictureControl: NEXT LBRACE TEXT RBRACE ;
pictureHighLight: HIGHLIGHT LBRACE TEXT RBRACE ;
pictureText: BEGINTEXT (formatCommand|mathCommand|inputCommand|enumeration|text|mathTable)* ENDTEXT;

picturePart: BEGINPART pictureText? (pictureInclude|pictureExclude|pictureControl|pictureHighLight|script)*  ENDPART;
pictureExtents: EXTENTS LBRACE TEXT RBRACE;
pictureScale: SCALE LBRACE TEXT RBRACE;
xTicks: XTICKS LBRACE TEXT RBRACE;
yTicks: YTICKS LBRACE TEXT RBRACE;
width: WIDTH LBRACE TEXT RBRACE;
dim: DIMENSION LBRACE TEXT RBRACE;

enumeration: enumerationStart item* enumerationEnd;
enumerationStart: BEGIN LBRACE ENUMERATE RBRACE (LBRACE symbol=TEXT RBRACE)?;
enumerationEnd: END LBRACE ENUMERATE RBRACE;

item: ITEM content ;
text:  (SPACES? TEXT) | NEWLINE;



