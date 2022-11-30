grammar MathPresentation;

@header {
    package dae.math.script;
}

presentation: presentationStart content presentationEnd ;
presentationStart: BEGIN LBRACE PRESENTATION RBRACE ;

content: title author? titleslide? ( include )* ;
presentationEnd: END LBRACE PRESENTATION RBRACE;

include: INCLUDE LBRACE TEXT RBRACE;
author: AUTHOR LBRACE TEXT RBRACE;
title: TITLE LBRACE TEXT RBRACE;
titleslide: TITLESLIDE LBRACE TEXT RBRACE;

BEGIN: '\\begin';
END: '\\end';
LBRACE: '{';
RBRACE: '}';
LPARENS: '(';
RPARENS: ')';
LBRACKET: '[';
RBRACKET: ']';

PRESENTATION: 'presentation';
INCLUDE: '\\include';
AUTHOR: '\\author';
TITLESLIDE: '\\titleslide';
TITLE: '\\title';
COMMA: ',';

TEXT: ~[{}\r\n>]+;
LINES : [ \t\r\n]+ -> skip ; // newlines
