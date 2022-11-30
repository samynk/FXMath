lexer grammar DocumentLexer;

@header {
    package dae.math.script;
}

NEWLINE: '\\\\';
BEGIN: '\\begin';
END: '\\end';
BACKSLASH: '\\';
LBRACE: '{';
RBRACE: '}';
LBRACKET: '[' -> pushMode(PARAMETERS);
VALUE: '{#'  -> pushMode(PARAMETERS);
CAPTION: '{$' -> pushMode(PARAMETERS);
FORMULA: '{&' -> pushMode(PARAMETERS);
INPUT: '{@' -> pushMode(PARAMETERS);

CDELIM: BACKSLASH '|';
MATHTABLE: 'mtable';
MATHROW: 'mrow';
BEGINMROW: BEGIN LBRACE MATHROW RBRACE;
ENDMROW: END LBRACE MATHROW RBRACE;

DOCUMENTSTART: BEGIN LBRACE 'document' RBRACE  ;
DOCUMENTEND: END LBRACE 'document' RBRACE;

BEGINPARAGRAPH: BEGIN LBRACE 'p' RBRACE;
ENDPARAGRAPH: END LBRACE 'p' RBRACE;
BEGINPART: BEGIN LBRACE 'part' RBRACE;
ENDPART: END LBRACE 'part' RBRACE;
BEGINTEXT: BEGIN LBRACE 'text' RBRACE;
ENDTEXT: END LBRACE 'text' RBRACE;
BEGINLISTING: BEGIN LBRACE 'listing' RBRACE;
ENDLISTING: END LBRACE 'listing' RBRACE;


BEGINSCRIPT: BEGIN LBRACE 'script' RBRACE -> pushMode(SCRIPT);
BEGINFORMULA: BEGIN LBRACE 'formula' RBRACE -> pushMode(DOCUMENTFORMULA);
BEGINCODE: BEGIN LBRACE 'code' RBRACE ->pushMode(DOCUMENTCODE);

PICTURE3D: 'picture3d';
PICTURE: 'picture';
RIGHTCOLUMN: 'rightcolumn';

ENUMERATE: 'enumerate';
LIST: 'list';
ITEMIZE: 'itemize';
ITEM: '\\item';
INCLUDE: '\\include';
EXCLUDE: '\\exclude';
NEXT: '\\next';
HIGHLIGHT: '\\highlight';
EXECUTE: '\\execute';
INCLUDESCRIPT: '\\includescript';
INCLUDEGRAPHICS: '\\includegraphics';
EXTENTS: '\\extents';
XTICKS: '\\xticks';
YTICKS: '\\yticks';
WIDTH: '\\width';
DIMENSION: '\\dimension';
SCALE: '\\scale';

TEXT: ~([\\{}\r\n]|'[')+;
SPACES: [ \t]+;
LINES : [\r\n]+ -> skip; // newlines

mode PARAMETERS;
PBACKSLASH: '\\';
FORMAT: '>>';
EQUALS: '=';
COMMA: ',';
PVALUES: '#(';
RPARENS: ')';
FVALUES: '&(';
CVALUES: '$(';
TRANSPOSE: '\'';
fragment GREEK : [\u03B1-\u03C9] ;
ID : (([a-zA-Z]|GREEK)+([a-zA-Z0-9]|GREEK)*) ;
DOT: '.';
NUMBER : [0-9]+ (DOT [0-9]+)? [°]?;
RBRACKET: ']' -> popMode;
PRBRACE: '}' -> popMode;

mode SCRIPT;
SCRIPTBLOCK: SCRIPTCONTENTS '\\end{script}' -> popMode;
fragment SCRIPTCONTENTS: .*?;

mode DOCUMENTFORMULA;
FORMULABLOCK: FORMULACONTENTS '\\end{formula}' -> popMode;
fragment FORMULACONTENTS: .*?;

mode DOCUMENTCODE;
CODEBLOCK: CODECONTENTS '\\end{code}' -> popMode;
fragment CODECONTENTS: .*?;
