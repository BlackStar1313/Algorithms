package structures;

import enums.*;

public final class Token
{

    public final TokenType type;
    public final double asNumber;
    public final OperatorType asOperator;
    public final Bracket asBracket;
    public final Function asFunction;


    public Token(double number)
    {
        type = TokenType.Numeric;
        asNumber = number;
        asOperator = OperatorType.None;
        asBracket = Bracket.None;
        asFunction = Function.None;
    }

    public Token(OperatorType operator)
    {
        assert operator != OperatorType.None;
        type = TokenType.Operator;
        asOperator = operator;
        asNumber = 0;
        asBracket = Bracket.None;
        asFunction = Function.None;
    }

    public Token(Bracket bracket)
    {
        assert bracket != Bracket.None;
        type = TokenType.Bracket;
        asBracket = bracket;
        asNumber = 0;
        asOperator = OperatorType.None;
        asFunction = Function.None;
    }

    public Token(Function function)
    {
        assert function != Function.None;
        type = TokenType.Function;
        asFunction = function;
        asBracket = Bracket.None;
        asNumber = 0;
        asOperator = OperatorType.None;
    }

    public int GetOperatorPrecedence()
    {
        assert this.type == TokenType.Operator;
        switch (asOperator)
        {
            case Power:
                return 3;
            case Divide:
            case Multiply:
                return 2;
            case Plus:
            case Minus:
                return 1;
            default:
                throw new IllegalArgumentException();
        }
    }

    public static Queue<Token> Tokenize(String input)
    {
        Queue<Token> tokenQueue = new Queue<>();
        for (String s : new String[]
                {"*", "+", "-", "/", "(", ")", "pi", "sin", "cos", "exp", "^", "e"}
        )
            input = input.replace(s, " " + s + " ");

        for (String s : input.split(" "))
        {
            if (s.isEmpty()) continue;
            switch (s)
            {
                case "+":
                    tokenQueue.Enqueue(new Token(OperatorType.Plus));
                    break;
                case "-":
                    tokenQueue.Enqueue(new Token(OperatorType.Minus));
                    break;
                case "*":
                    tokenQueue.Enqueue(new Token(OperatorType.Multiply));
                    break;
                case "/":
                    tokenQueue.Enqueue(new Token(OperatorType.Divide));
                    break;
                case "^":
                    tokenQueue.Enqueue(new Token(OperatorType.Power));
                    break;
                case "(":
                    tokenQueue.Enqueue(new Token(Bracket.Open));
                    break;
                case ")":
                    tokenQueue.Enqueue(new Token(Bracket.Close));
                    break;
                case "e":
                    tokenQueue.Enqueue(new Token(Math.E));
                    break;
                case "pi":
                    tokenQueue.Enqueue(new Token(Math.PI));
                    break;
                case "exp":
                    tokenQueue.Enqueue(new Token(Function.Exp));
                    break;
                case "sin":
                    tokenQueue.Enqueue(new Token(Function.Sin));
                    break;
                case "cos":
                    tokenQueue.Enqueue(new Token(Function.Cos));
                    break;
                default:
                    tokenQueue.Enqueue(new Token(Double.parseDouble(s)));
                    break;
            }
        }
        return tokenQueue;
    }

    @Override
    public String toString()
    {
        switch (this.type)
        {
            case Bracket:
                if (this.asBracket == Bracket.Open) return "( ";
                else return ") ";
            case Operator:
                switch (this.asOperator)
                {
                    case Plus:
                        return "+ ";
                    case Minus:
                        return "- ";
                    case Multiply:
                        return "* ";
                    case Divide:
                        return "/ ";
                    case Power:
                        return "^ ";
                }
            case Function:
                switch (this.asFunction)
                {
                    case Sin:
                        return "sin ";
                    case Cos:
                        return "cos ";
                    case Exp:
                        return "exp ";
                }
            case Numeric:
                if (this.asNumber - Math.round(this.asNumber) == 0.0)
                {
                    return Long.toString(Math.round(this.asNumber)) + " ";
                }
                return Double.toString(this.asNumber) + " ";
        }
        throw new IllegalArgumentException();
    }

}
