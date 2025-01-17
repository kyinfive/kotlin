/*
 * Copyright 2010-2020 JetBrains s.r.o. and Kotlin Programming Language contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.fir.serialization

import org.jetbrains.kotlin.fir.FirSession
import org.jetbrains.kotlin.fir.expressions.*
import org.jetbrains.kotlin.fir.expressions.impl.FirResolvedArgumentList
import org.jetbrains.kotlin.fir.resolve.toSymbol
import org.jetbrains.kotlin.fir.serialization.constant.ConstantValue
import org.jetbrains.kotlin.fir.serialization.constant.toConstantValue
import org.jetbrains.kotlin.fir.types.*
import org.jetbrains.kotlin.metadata.ProtoBuf
import org.jetbrains.kotlin.name.Name

class FirAnnotationSerializer(private val session: FirSession, internal val stringTable: FirElementAwareStringTable) {
    fun serializeAnnotation(annotation: FirAnnotationCall): ProtoBuf.Annotation = ProtoBuf.Annotation.newBuilder().apply {
        val lookupTag = annotation.typeRef.coneTypeSafe<ConeClassLikeType>()?.lookupTag
            ?: error { "Annotation without proper lookup tag: ${annotation.annotationTypeRef.coneType}" }

        id = lookupTag.toSymbol(session)?.let { stringTable.getFqNameIndex(it.fir) }
            ?: stringTable.getQualifiedClassNameIndex(lookupTag.classId)

        fun addArgument(argumentExpression: FirExpression, parameterName: Name) {
            val argument = ProtoBuf.Annotation.Argument.newBuilder()
            argument.nameId = stringTable.getStringIndex(parameterName.asString())
            argument.setValue(valueProto(argumentExpression.toConstantValue() ?: return))
            addArgument(argument)
        }

        val argumentList = annotation.argumentList
        if (argumentList is FirResolvedArgumentList) {
            for ((argumentExpression, parameter) in argumentList.mapping) {
                addArgument(argumentExpression, parameter.name)
            }
        } else {
            for (argumentExpression in argumentList.arguments) {
                if (argumentExpression !is FirNamedArgumentExpression) continue
                addArgument(argumentExpression, argumentExpression.name)
            }
        }
    }.build()

    internal fun valueProto(constant: ConstantValue<*>): ProtoBuf.Annotation.Argument.Value.Builder =
        ProtoBuf.Annotation.Argument.Value.newBuilder().apply {
            constant.accept(
                FirAnnotationArgumentVisitor,
                FirAnnotationArgumentVisitorData(this@FirAnnotationSerializer, this)
            )
        }
}
