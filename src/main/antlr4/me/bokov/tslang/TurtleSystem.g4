grammar TurtleSystem;

parse
    : ( (SEMICOLON | NEWLINE)* ( turtle_system_lang_expression | error ) (SEMICOLON | NEWLINE)* )+ (NEWLINE)* EOF;

error
    : UNEXPECTED_CHAR+
            {
    			throw new RuntimeException("UNEXPECTED_CHAR=" + $UNEXPECTED_CHAR.text);
    		}
    ;

turtle_system_lang_expression
    : symbol_expression
    | rule_expression
    | start_expression
    ;

symbol_expression
    : KW_SYMBOL IDENTIFIER turtle_expression_block
    ;

rule_expression
    : KW_RULE IDENTIFIER probability? ARROW BLOCK_OPEN symbol_name_list BLOCK_CLOSE
    ;

start_expression
    : KW_START WHITESPACE* BLOCK_OPEN WHITESPACE* symbol_name_list WHITESPACE* BLOCK_CLOSE
    ;

symbol_name_list
    : (IDENTIFIER)+
    ;

turtle_expression_block :
    BLOCK_OPEN
    turtle_expression
    (SEMICOLON turtle_expression)*
    BLOCK_CLOSE
    ;

turtle_expression
    : turtle_up
    | turtle_down
    | turtle_push
    | turtle_pop
    | turtle_forward
    | turtle_backward
    | turtle_left
    | turtle_right
    | turtle_color
    | turtle_rotate
    | turtle_width
    ;

turtle_up
    : KW_UP
    ;
turtle_down
    : KW_DOWN
    ;
turtle_push
    : KW_PUSH
    ;
turtle_pop
    : KW_POP
    ;
turtle_color
    : KW_COLOR color_arg_in_parantheses
    ;
turtle_forward
    : KW_FORWARD LEFTPAR calc_expression RIGHTPAR
    ;
turtle_backward
    : KW_BACKWARD LEFTPAR calc_expression RIGHTPAR
    ;
turtle_left
    : KW_LEFT LEFTPAR calc_expression RIGHTPAR
    ;
turtle_right
    : KW_RIGHT LEFTPAR calc_expression RIGHTPAR
    ;
turtle_rotate
    : KW_ROTATE LEFTPAR calc_expression RIGHTPAR
    ;
turtle_width
    : KW_WIDTH LEFTPAR calc_expression RIGHTPAR
    ;

calc_expression
    : calc_expression OPERATOR calc_expression #BinaryExpression
    | OPERATOR calc_expression #UnaryExpression
    | LEFTPAR calc_expression RIGHTPAR #ParanthesesExpression
    | function_call_expression #FunctionCallExpression
    | (DECIMAL) #LiteralExpression
    | (IDENTIFIER) #VariableExpression
    ;

function_call_expression
    : IDENTIFIER LEFTPAR function_call_parameter_list RIGHTPAR
    ;

function_call_parameter_list
    : calc_expression (',' calc_expression)*
    |
    ;

decimal_arg_in_parantheses : LEFTPAR DECIMAL RIGHTPAR;
integer_arg_in_parantheses : LEFTPAR INTEGER RIGHTPAR;
color_arg_in_parantheses : LEFTPAR COLOR RIGHTPAR;

probability: ARR_OPEN DECIMAL ARR_CLOSE;

OPERATOR
    : OP_ADD
    | OP_SUB
    | OP_MUL
    | OP_DIV
    | OP_MOD
    ;

OP_ADD : '+';
OP_SUB : '-';
OP_MUL : '*';
OP_DIV : '/';
OP_MOD : '%';

KW_SYMBOL : S Y M B O L;
KW_RULE : R U L E;
KW_START : S T A R T;

KW_UP : U P;
KW_DOWN : D O W N;
KW_COLOR : C O L O R;
KW_FORWARD : F O R W A R D;
KW_BACKWARD : B A C K W A R D;
KW_LEFT : L E F T;
KW_RIGHT : R I G H T;
KW_PUSH : P U S H;
KW_POP : P O P;
KW_WIDTH  : W I D T H;
KW_ROTATE : R O T A T E;

COLOR : (COLOR_RGB_HEX | COLOR_RGBA_HEX);

COLOR_RGB_HEX : HEX_CHAR HEX_CHAR HEX_CHAR HEX_CHAR HEX_CHAR HEX_CHAR;
COLOR_RGBA_HEX : HEX_CHAR HEX_CHAR HEX_CHAR HEX_CHAR HEX_CHAR HEX_CHAR HEX_CHAR HEX_CHAR;
DECIMAL : (NEGATIVE_SIGN)? (INTEGER) (DOT INTEGER)?;
INTEGER : (NEGATIVE_SIGN)? (DIGIT)+;
IDENTIFIER : (LETTER | UNDERSCORE) (LETTER | DIGIT | UNDERSCORE)*;

WHITESPACE : [ \t]+ -> channel(HIDDEN);
COMMENT: '#' ~[\r\n]* -> channel(HIDDEN);

NEWLINE : '\r'? '\n';
SEMICOLON : ';';
LEFTPAR : '(';
RIGHTPAR : ')';
BLOCK_OPEN: '{';
BLOCK_CLOSE: '}';
ARR_OPEN: '[';
ARR_CLOSE: ']';
ARROW: '->';
DOT: '.';
NEGATIVE_SIGN: '-';
UNDERSCORE : '_';

UNEXPECTED_CHAR
	: .
	;

fragment DIGIT: [0-9];
fragment LETTER: [a-zA-Z];
fragment HEX_CHAR: [0-9a-fA-F];

fragment A : [aA];
fragment B : [bB];
fragment C : [cC];
fragment D : [dD];
fragment E : [eE];
fragment F : [fF];
fragment G : [gG];
fragment H : [hH];
fragment I : [iI];
fragment J : [jJ];
fragment K : [kK];
fragment L : [lL];
fragment M : [mM];
fragment N : [nN];
fragment O : [oO];
fragment P : [pP];
fragment Q : [qQ];
fragment R : [rR];
fragment S : [sS];
fragment T : [tT];
fragment U : [uU];
fragment V : [vV];
fragment W : [wW];
fragment X : [xX];
fragment Y : [yY];
fragment Z : [zZ];