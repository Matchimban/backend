package com.project.matchimban.common.global.annotation;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;

@AttributeOverride(name = "updatedDate", column = @Column(insertable = false, updatable = false))
public @interface DoNotUseUpdatedDate {
}
