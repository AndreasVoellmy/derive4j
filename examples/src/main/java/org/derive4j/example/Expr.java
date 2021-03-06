/*
 * Copyright (c) 2015, Jean-Baptiste Giraudeau <jb@giraudeau.info>
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *  * Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 *  * Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *  * Neither the name of the copyright holder nor the names of its contributors
 *    may be used to endorse or promote products derived from this software
 *    without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package org.derive4j.example;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.IntFunction;
import org.derive4j.Data;
import org.derive4j.FieldNames;

import static org.derive4j.example.Exprs.Add;
import static org.derive4j.example.Exprs.Const;
import static org.derive4j.example.Exprs.Mult;

@Data
public abstract class Expr {

  public static Integer eval(Expr expression) {

    return expression.match(i -> i, (left, right) -> eval(left) + eval(right), (left, right) -> eval(left) * eval(right),
        (expr) -> -eval(expr));
  }

  public static void main(String[] args) {

    Expr expr = Add(Const(1), Mult(Const(2), Mult(Const(3), Const(3))));
    System.out.println(eval(expr)); // (1+(2*(3*3))) = 19
  }

  public abstract <R> R match(@FieldNames("value") IntFunction<R> Const,
      @FieldNames({ "left", "right" }) BiFunction<Expr, Expr, R> Add,
      @FieldNames({ "left", "right" }) BiFunction<Expr, Expr, R> Mult, @FieldNames("expr") Function<Expr, R> Neg);

}