grammar MathScript;

@header {
    package dae.math.script;
}

script: (expression|function)+ EOF;
function: DEF functionID=ID LPARENS (ID (COMMA ID)*)? RPARENS LBRACE expression* RBRACE;

expression: (assignment|space|(debug SEMICOLUMN)|(call SEMICOLUMN));
space: SPACE ID LBRACE expression* RBRACE;
debug: DEBUG LPARENS (ID (COMMA ID)*)? RPARENS;
assignment: reference EQUALS ( constructor | method | calculation | call ) SEMICOLUMN;
reference: ID (LBRACKET INT (COMMA INT)* RBRACKET)?;

call: ID DOT ID (LPARENS calculation (COMMA calculation)* RPARENS)?;
constructor: NEW ID LPARENS (formula (COMMA formula)*)? RPARENS;
method: ID LPARENS formula (COMMA formula)* RPARENS;
formula:
 op=MINUS formula                     
 | formula op=POWER formula
 | formula op=(TIMES | DIVIDEBY | MODULO) formula  
 | formula op=(PLUS | MINUS) formula
 | value
 | method
 | LPARENS formula RPARENS;

boolexpr: op=NOT boolexpr
    | formula cop=comparisonop formula
    | boolexpr lop=logicalop boolexpr
    | BOOLEAN
    | LPARENS boolexpr RPARENS;

comparisonop: EQ|NEQ|GT|GTEQ|LT|LTEQ;
logicalop: AND|OR;
 
value: INT | FLOAT | float2 | float3 | matrix | STRING | reference ;

calculation: formula | boolexpr;


float2: LBRACKET formula COMMA formula RBRACKET;
float3: LBRACKET formula COMMA formula COMMA formula RBRACKET;
matrix: LBRACKET (formula (COMMA formula)*) (SEMICOLUMN formula (COMMA formula)*)* RBRACKET;
operator: PLUS | MINUS | DIVIDEBY | TIMES | MODULO;

NEW: 'new';
SPACE: 'space';
DEBUG: 'debug';
DEF : 'def';
BACKSLASH: '\\';
EQUALS: '=';
PLUS: '+';
MINUS: '-';
DIVIDEBY: '/';
TIMES: '*';
MODULO: '%';
POWER: '^';
LT : '<';
LTEQ: '<=';
GT : '>';
GTEQ: '>=';
EQ: '==';
NEQ: '!=';
NOT: '!';
AND: 'and';
OR: 'or';
LBRACE: '{';
RBRACE: '}';
LPARENS: '(';
RPARENS: ')';
LBRACKET: '[';
RBRACKET: ']';
DOT: '.';
COMMA: ',';
SEMICOLUMN:';';
DEGREE: '°';

INT: [0-9]+[°]?;
FLOAT: ([0-9]* DOT [0-9]+[°]?);
STRING: ["]~('"')*["];
BOOLEAN: 'true' | 'false';
//ID : ([a-z]|[A-Z])+[a-zA-Z0-9]* ;
fragment GREEK : [\u03B1-\u03C9] ;
ID : (([a-zA-Z]|GREEK)+([a-zA-Z0-9]|GREEK)*) ;
LINES : [ \t\r\n]+ -> skip ; // newlines
