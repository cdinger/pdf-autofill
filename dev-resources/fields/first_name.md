# first_name

Returns the current user's first name

```SQL
select first_name
from ps_names n
where eff_status = 'A'
  and name_type = 'PRI'
  and effdt = (
    select max(effdt)
    from ps_names
    where emplid = n.emplid
      and name_type = n.name_type
      and effdt <= sysdate
  )
```
