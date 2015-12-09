select lower(alter_emplid)
from ps_hr.ps_pers_data_effdt pde
where emplid = :principal_id
  and effdt = (
    select max(effdt)
    from ps_hr.ps_pers_data_effdt
    where emplid = pde.emplid
      and effdt <= sysdate
  )
